package com.company.gamestore.service;

import com.company.gamestore.Exceptions.NotFoundException;
import com.company.gamestore.models.Console;
import com.company.gamestore.models.Game;
import com.company.gamestore.models.Invoice;
import com.company.gamestore.models.TShirt;
import com.company.gamestore.repositories.*;
import com.company.gamestore.models.InvoiceViewModel;
import com.company.gamestore.repositories.TShirtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@ComponentScan("com.company.gamestore.repositories")
public class ServiceLayer {
    private ConsoleRepository consoleRepository;

    private GameRepository gameRepository;
    private TShirtsRepository tShirtsRepository;

    private InvoiceRepository invoiceRepository;

    private FeeRepository feeRepository;

    private TaxRepository taxRepository;


    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, GameRepository gameRepository,
                        TShirtsRepository tShirtsRepository, InvoiceRepository invoiceRepository,
                        FeeRepository feeRepository, TaxRepository taxRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tShirtsRepository = tShirtsRepository;
        this.invoiceRepository = invoiceRepository;
        this.feeRepository = feeRepository;
        this.taxRepository = taxRepository;
    }

    // Invoice API

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setId(invoice.getId());
        ivm.setName(invoice.getName());
        ivm.setStreet(invoice.getStreet());
        ivm.setCity(invoice.getCity());
        ivm.setState(invoice.getState());
        ivm.setZipcode(invoice.getZipcode());
        ivm.setItemType(invoice.getItemType());
        ivm.setItemId(invoice.getItemId());
        ivm.setQuantity(invoice.getQuantity());
        ivm.setUnitPrice(invoice.getUnitPrice());
        ivm.setTax(invoice.getTax());
        ivm.setSubtotal(invoice.getSubtotal());
        ivm.setProcessingFee(invoice.getProcessingFee());
        ivm.setTotal(invoice.getTotal());

        return ivm;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) {

        validateOrderRequest(viewModel);

        Invoice invoice = new Invoice();
        invoice.setName(viewModel.getName());
        invoice.setStreet(viewModel.getStreet());
        invoice.setCity(viewModel.getCity());
        invoice.setState(viewModel.getState());
        invoice.setZipcode(viewModel.getZipcode());
        invoice.setItemType(viewModel.getItemType());
        invoice.setItemId(viewModel.getItemId());
        invoice.setQuantity(viewModel.getQuantity());

        invoice = calculateInvoiceTotal(invoice);
        invoice = invoiceRepository.save(invoice);

        viewModel.setId(invoice.getId());
        viewModel.setUnitPrice(invoice.getUnitPrice());
        viewModel.setSubtotal(invoice.getSubtotal());
        viewModel.setTax(invoice.getTax());
        viewModel.setProcessingFee(invoice.getProcessingFee());
        viewModel.setTotal(invoice.getTotal());

        return viewModel;
    }

    public InvoiceViewModel findInvoiceById(Integer id) {
        Optional<Invoice> returnedInvoice = invoiceRepository.findById(id);
        return returnedInvoice.isPresent() ? buildInvoiceViewModel(returnedInvoice.get()) : null;
    }

    public List<InvoiceViewModel> findAllInvoices() {
        List<Invoice> returnedInvoices = invoiceRepository.findAll();

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for(Invoice invoice: returnedInvoices) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            ivmList.add(ivm);
        }

        return ivmList;
    }

    public List<InvoiceViewModel> findInvoiceByName(String name) {
        List<Invoice> returnedInvoices = invoiceRepository.findInvoiceByName(name);

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for(Invoice invoice: returnedInvoices) {
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            ivmList.add(ivm);
        }

        return ivmList;
    }

    //   TShirt API
//    @Autowired
//    public ServiceLayer(TShirtsRepository tShirtsRepository) {
//        this.tShirtsRepository = tShirtsRepository;
//    }

    public TShirt saveTShirt(TShirt tShirt) {
        return tShirtsRepository.save(tShirt);
    }

    public TShirt findTShirt(int id) {
        Optional<TShirt> tShirtOptional = tShirtsRepository.findById(id);
        return tShirtOptional.isPresent() ? tShirtOptional.get() : null;
    }

    public List<TShirt> findAllTShirts() {
        return tShirtsRepository.findAll();
    }

    public void updateTShirt(TShirt tShirt) {
        tShirtsRepository.save(tShirt);
    }

    public void deleteTShirt(int id) {
        tShirtsRepository.deleteById(id);
    }

    public List<TShirt> findTShirtByColor(String color) {
        return tShirtsRepository.findByColor(color);
    }

    public List<TShirt> findTShirtBySize(String size) {
        return tShirtsRepository.findBySize(size);
    }


    //   Console API
    public Console findConsoleById(int id){
        Optional<Console> consoleOptional = consoleRepository.findById(id);
        return consoleOptional.isPresent() ? consoleOptional.get() : null;
    }

    public List<Console> findAllConsoles(){
        return consoleRepository.findAll();
    }

    public void updateConsole(Console console){
        consoleRepository.save(console);
    }

    public void deleteConsole(int id){
        consoleRepository.deleteById(id);
    }


    //   Game API




    public void validateOrderRequest(InvoiceViewModel viewModel) {

        String itemType = viewModel.getItemType();

        if (!(itemType.equals("Console") || itemType.equals("Game") || itemType.equals("TShirt"))) {
            throw new IllegalArgumentException("Invalid item type");
        }

        //The amount ordered should not exceed the quantity of items present in the inventory.
        if (itemType.equals("Console")) {
            Optional<Console> console = consoleRepository.findById(viewModel.getItemId());

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
            Optional<Game> game = gameRepository.findById(viewModel.getItemId());

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
            Optional<TShirt> tShirt = tShirtsRepository.findById(viewModel.getItemId());

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
        if (taxRepository.findById(viewModel.getState()).isEmpty()) {
            throw new NotFoundException("Invalid State");
        }
    }

    public Invoice calculateInvoiceTotal(Invoice invoice) {
        BigDecimal unitPrice = null;
        String itemType = invoice.getItemType();

        if (itemType.equals("Console")) {
            Console console = consoleRepository.findById(invoice.getItemId()).get();
            unitPrice = console.getPrice();

            console.setQuantity(console.getQuantity() - invoice.getQuantity());
            consoleRepository.save(console);
        }

        else if (itemType.equals("Game")) {
            Game game = gameRepository.findById(invoice.getItemId()).get();
            unitPrice = game.getPrice();

            game.setQuantity(game.getQuantity() - invoice.getQuantity());
            gameRepository.save(game);

        }

        else if (itemType.equals("TShirt")) {
            TShirt tShirt = tShirtsRepository.findById(invoice.getItemId()).get();
            unitPrice = tShirt.getPrice();

            tShirt.setQuantity(tShirt.getQuantity() - invoice.getQuantity());
            tShirtsRepository.save(tShirt);
        }

        BigDecimal processingFee = feeRepository.findById(itemType).get().getFee();
        BigDecimal tax = taxRepository.findById(invoice.getState()).get().getRate();
        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(invoice.getQuantity()));
        BigDecimal total = subtotal.add((subtotal.multiply(tax)).add(processingFee));

        if (invoice.getQuantity() > 10) {
            total = total.add(new BigDecimal("15.49"));
        }

        invoice.setUnitPrice(unitPrice);
        invoice.setSubtotal(subtotal);
        invoice.setProcessingFee(processingFee);
        invoice.setTax(tax);
        invoice.setTotal(total);

        return invoice;
    }
}
