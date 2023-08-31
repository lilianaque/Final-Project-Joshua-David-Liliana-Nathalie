package com.company.gamestore.service;

import com.company.gamestore.Exceptions.NotFoundException;
import com.company.gamestore.models.*;
import com.company.gamestore.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Best practice, don't put any logic in controllers, put it in service layer.
@Component
public class ServiceLayer {
    private InvoiceRepository invoiceRepo;
    private ConsoleRepository consoleRepo;
    private TShirtsRepository tshirtRepo;
    private TaxRepository taxRepo;
    private GameRepository gameRepo;
    private FeeRepository feeRepo;

    @Autowired
    public ServiceLayer(
            InvoiceRepository invoiceRepo,
            ConsoleRepository consoleRepo,
            TShirtsRepository tshirtRepo,
            TaxRepository taxRepo,
            GameRepository gameRepo,
            FeeRepository feeRepo
    ) {
        this.invoiceRepo = invoiceRepo;
        this.consoleRepo = consoleRepo;
        this.tshirtRepo = tshirtRepo;
        this.taxRepo = taxRepo;
        this.gameRepo = gameRepo;
        this.feeRepo = feeRepo;
    }

    @Transactional
    public Invoice createInvoice(InvoiceViewModel ivm) {
        Invoice toReturn = new Invoice();
        String tableName = ivm.getItemType();
        int itemId = ivm.getItemId();
        int quantity = ivm.getQuantity();
//        cast all shared fields to invoice object
        toReturn.setName(ivm.getCustomerName());
        toReturn.setStreet(ivm.getStreet());
        toReturn.setCity(ivm.getCity());
        toReturn.setState(ivm.getState());
        toReturn.setZipcode(ivm.getZipcode());
        toReturn.setItem_type(tableName);
        toReturn.setItem_id(itemId);
        toReturn.setQuantity(quantity);

//        get table name, item id, and quantity from ivm for processing

        BigDecimal processingFee = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal taxRate = BigDecimal.ZERO;

//        Make sure item is in stock, set unit price, and subtract quantity from stock
        if (tableName.equals("Game")) {
            Game game = gameRepo.findById(itemId).orElse(null);
            BigDecimal unitPrice = BigDecimal.valueOf(game.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);
            if (game.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough games in stock");
            }
            game.setQuantity(game.getQuantity() - quantity);
            gameRepo.save(game);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        if (tableName.equals("Console")) {
            Console console = consoleRepo.findById(itemId).orElse(null);
            BigDecimal unitPrice = BigDecimal.valueOf(console.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);

            if (console.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough consoles in stock");
            }
            console.setQuantity(console.getQuantity() - quantity);
            consoleRepo.save(console);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
        if (tableName.equals("T-Shirt")) {
            TShirt tshirt = tshirtRepo.findById(itemId).orElse(null);
            BigDecimal unitPrice = BigDecimal.valueOf(tshirt.getPrice().doubleValue());
            toReturn.setUnit_price(unitPrice);

            if (tshirt.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough tshirts in stock");
            }
            tshirt.setQuantity(tshirt.getQuantity() - quantity);
            tshirtRepo.save(tshirt);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        }
// Set subtotal
        toReturn.setSubtotal(total.setScale(2, BigDecimal.ROUND_HALF_EVEN));

// Adding tax
        taxRate = taxRepo.findByState(ivm.getState()).getRate();
        toReturn.setTax(total.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_EVEN));

// add tax to total
        total = total.multiply(BigDecimal.ONE.add(taxRate));

// Adding processing fee
        processingFee = feeRepo.findByProductType(tableName).getFee();

        if (quantity > 10) {
            processingFee = processingFee.add(BigDecimal.valueOf(15.49));
        }
        toReturn.setProcessing_fee(processingFee.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        total = total.add(processingFee);

        toReturn.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        return toReturn;
    }


    //   TShirt API
//    @Autowired
//    public ServiceLayer(TShirtsRepository tShirtsRepository) {
//        this.tShirtsRepository = tShirtsRepository;
//    }

    public TShirt saveTShirt(TShirt tShirt) {
        return tshirtRepo.save(tShirt);
    }

    public TShirt findTShirt(int id) {
        Optional<TShirt> tShirtOptional = tshirtRepo.findById(id);
        return tShirtOptional.isPresent() ? tShirtOptional.get() : null;
    }

    public List<TShirt> findAllTShirts() {
        return tshirtRepo.findAll();
    }

    public void updateTShirt(TShirt tShirt) {
        tshirtRepo.save(tShirt);
    }

    public void deleteTShirt(int id) {
        tshirtRepo.deleteById(id);
    }

    public List<TShirt> findTShirtByColor(String color) {
        return tshirtRepo.findByColor(color);
    }

    public List<TShirt> findTShirtBySize(String size) {
        return tshirtRepo.findBySize(size);
    }


    //   Console API
    public Console findConsoleById(int id){
        Optional<Console> consoleOptional = consoleRepo.findById(id);
        return consoleOptional.isPresent() ? consoleOptional.get() : null;
    }

    public List<Console> findAllConsoles(){
        return consoleRepo.findAll();
    }

    public void updateConsole(Console console){
        consoleRepo.save(console);
    }

    public void deleteConsole(int id){
        consoleRepo.deleteById(id);
    }



    public void validateOrderRequest(InvoiceViewModel viewModel) {

        String itemType = viewModel.getItemType();

        if (!(itemType.equals("Console") || itemType.equals("Game") || itemType.equals("TShirt"))) {
            throw new IllegalArgumentException("Invalid item type");
        }

        //The amount ordered should not exceed the quantity of items present in the inventory.
        if (itemType.equals("Console")) {
            Optional<Console> console = consoleRepo.findById(viewModel.getItemId());

            if (console.isEmpty()) {
                throw new NotFoundException("Invalid console ID");
            }
            else {
                if (viewModel.getQuantity() > console.get().getQuantity()) {
                    throw new IllegalArgumentException("The requested amount surpasses the quantity of consoles currently in stock.");
                }
            }
        }

        else if (itemType.equals("Game")) {
            Optional<Game> game = gameRepo.findById(viewModel.getItemId());

            if (game.isEmpty()) {
                throw new NotFoundException("Invalid game ID");
            }
            else {
                if (viewModel.getQuantity() > game.get().getQuantity()) {
                    throw new IllegalArgumentException("The requested amount surpasses the quantity of games currently in stock ");
                }
            }
        }

        else if (itemType.equals("TShirt")) {
            Optional<TShirt> tShirt = tshirtRepo.findById(viewModel.getItemId());

            if (tShirt.isEmpty()) {
                throw new NotFoundException("Invalid tShirt ID");
            }
            else {
                if (viewModel.getQuantity() > tShirt.get().getQuantity()) {
                    throw new IllegalArgumentException("The requested amount surpasses the quantity of t-shirts currently in stock.");
                }
            }
        }

        //validate state code.
        if (taxRepo.findById(viewModel.getState()).isEmpty()) {
            throw new NotFoundException("Invalid State");
        }
    }

}
