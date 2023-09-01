package com.company.gamestore.service;

import com.company.gamestore.models.*;
import com.company.gamestore.repositories.*;
import com.company.gamestore.models.InvoiceViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {
    ServiceLayer service;
    ConsoleRepository consoleRepo;
    FeeRepository feeRepo;
    GameRepository gameRepo;
    InvoiceRepository invoiceRepo;
    TaxRepository taxRepo;
    TShirtsRepository tshirtRepo;

    @BeforeEach
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        setUpGameRepositoryMock();
        setUpTshirtRepositoryMock();
        setUpInvoiceRepositoryMock();
        setUpFeeRepositoryMock();
        setUpTaxRepositoryMock();
        service = new ServiceLayer(invoiceRepo, consoleRepo, tshirtRepo, taxRepo, gameRepo, feeRepo);

    }

    //   Invoice API Tests
    @Test
    public void shouldCreateInvoice(){
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setItemType("Game");
        ivm.setItemId(1);
        ivm.setQuantity(5);
        ivm.setState("TX");

        Game game = new Game();
        game.setId(1);
        game.setPrice(new BigDecimal("100.00"));
        game.setQuantity(10);

        Tax tax = new Tax();
        tax.setState("TX");
        tax.setRate(new BigDecimal("0.10"));

        Fee fee = new Fee();
        fee.setProductType("Game");
        fee.setFee(new BigDecimal("5.00"));

        when(gameRepo.findById(1)).thenReturn(Optional.of(game));
        when(taxRepo.findByState("TX")).thenReturn(tax);
        when(feeRepo.findByProductType("Game")).thenReturn(fee);

        // Act
        Invoice invoice = service.createInvoice(ivm);

        // Assert
        assertEquals(new BigDecimal("100.0"), invoice.getUnit_price());
        assertEquals(new BigDecimal("500.00"), invoice.getSubtotal());
        assertEquals(new BigDecimal("50.00"), invoice.getTax());
        assertEquals(new BigDecimal("5.00"), invoice.getProcessing_fee());
        assertEquals(new BigDecimal("555.00"), invoice.getTotal());

    }

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenNotEnoughGamesInStock() {
//        InvoiceViewModel ivm = new InvoiceViewModel();
//        ivm.setItemType("Game");
//        ivm.setItemId(1);
//        ivm.setQuantity(15);  // Requested quantity is more than available
//
//        Game game = new Game();
//        game.setId(1);
//        game.setPrice(BigDecimal.valueOf(10.00));
//        game.setQuantity(10);  // Only 10 games available
//
//        when(gameRepo.findById(1)).thenReturn(Optional.of(game));
//
//        serviceLayer.createInvoice(ivm);
//    }


//}

    @Test
    public void shouldFindInvoiceByName(){
        Invoice invoice = new Invoice();
        invoice.setItem_id(1);
        invoice.setCity("New York");
        invoice.setQuantity(5);
        invoice.setName("Josh");
        invoice.setItem_id(1);
        invoice.setItem_type("type");
        invoice.setState("NY");
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setTax(BigDecimal.valueOf(12.99));
        invoice.setStreet("fake st");
        invoice.setSubtotal(BigDecimal.valueOf(100.45));
        invoice.setTotal(BigDecimal.valueOf(113.15));
        invoice.setZipcode("11111");
        invoice.setUnit_price(BigDecimal.valueOf(12.99));

        InvoiceViewModel expectedIvm = new InvoiceViewModel();
        expectedIvm.setItemId(1);
        expectedIvm.setCity("New York");
        expectedIvm.setQuantity(5);
        expectedIvm.setCustomerName("Josh");
        expectedIvm.setItemId(2);
        expectedIvm.setItemType("type");
        expectedIvm.setState("NY");
        expectedIvm.setProcessingFee(BigDecimal.valueOf(1.99));
        expectedIvm.setTax(BigDecimal.valueOf(12.99));
        expectedIvm.setStreet("fake st");
        expectedIvm.setSubtotal(BigDecimal.valueOf(100.45));
        expectedIvm.setTotal(BigDecimal.valueOf(113.15));
        expectedIvm.setZipcode("11111");
        expectedIvm.setUnitPrice(BigDecimal.valueOf(12.99));

        when(invoiceRepo.findByName("Josh")).thenReturn(Arrays.asList(invoice));

        // Act
        List<InvoiceViewModel> result = service.findInvoiceByName("Josh");

        // Assert
        assertFalse(result.isEmpty(), "Expected non-empty result");
        assertEquals(expectedIvm.getCustomerName(), result.get(0).getCustomerName());
    }

    @Test
    public void shouldFindInvoiceById(){
        Invoice invoice = new Invoice();
        invoice.setItem_id(1);
        invoice.setCity("New York");
        invoice.setQuantity(5);
        invoice.setName("Josh");
        invoice.setItem_id(1);
        invoice.setItem_type("type");
        invoice.setState("NY");
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setTax(BigDecimal.valueOf(12.99));
        invoice.setStreet("fake st");
        invoice.setSubtotal(BigDecimal.valueOf(100.45));
        invoice.setTotal(BigDecimal.valueOf(113.15));
        invoice.setZipcode("11111");
        invoice.setUnit_price(BigDecimal.valueOf(12.99));

        InvoiceViewModel expectedIvm = new InvoiceViewModel();
        expectedIvm.setItemId(1);
        expectedIvm.setCity("New York");
        expectedIvm.setQuantity(5);
        expectedIvm.setCustomerName("Josh");
        expectedIvm.setItemId(1);
        expectedIvm.setItemType("type");
        expectedIvm.setState("NY");
        expectedIvm.setProcessingFee(BigDecimal.valueOf(1.99));
        expectedIvm.setTax(BigDecimal.valueOf(12.99));
        expectedIvm.setStreet("fake st");
        expectedIvm.setSubtotal(BigDecimal.valueOf(100.45));
        expectedIvm.setTotal(BigDecimal.valueOf(113.15));
        expectedIvm.setZipcode("11111");
        expectedIvm.setUnitPrice(BigDecimal.valueOf(12.99));

        when(invoiceRepo.findById(1)).thenReturn(Optional.of(invoice));

        // Act
        InvoiceViewModel result = service.findInvoiceById(1);

        // Assert
        assertNotNull(result, "Expected non-null result");
        assertEquals(expectedIvm.getItemId(), result.getItemId());
    }

    @Test
    public void shouldAllInvoices(){
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setItemId(1);
        invoice.setCity("New York");
        invoice.setQuantity(5);
        invoice.setCustomerName("Josh");
        invoice.setItemId(1);
        invoice.setItemType("type");
        invoice.setState("NY");
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setTax(BigDecimal.valueOf(12.99));
        invoice.setStreet("fake st");
        invoice.setSubtotal(BigDecimal.valueOf(100.45));
        invoice.setTotal(BigDecimal.valueOf(113.15));
        invoice.setZipcode("11111");
        invoice.setUnitPrice(BigDecimal.valueOf(12.99));

        InvoiceViewModel expectedIvm = new InvoiceViewModel();
        expectedIvm.setItemId(1);
        expectedIvm.setCity("New York");
        expectedIvm.setQuantity(5);
        expectedIvm.setCustomerName("Josh");
        expectedIvm.setItemId(2);
        expectedIvm.setItemType("type");
        expectedIvm.setState("NY");
        expectedIvm.setProcessingFee(BigDecimal.valueOf(1.99));
        expectedIvm.setTax(BigDecimal.valueOf(12.99));
        expectedIvm.setStreet("fake st");
        expectedIvm.setSubtotal(BigDecimal.valueOf(100.45));
        expectedIvm.setTotal(BigDecimal.valueOf(113.15));
        expectedIvm.setZipcode("11111");
        expectedIvm.setUnitPrice(BigDecimal.valueOf(12.99));



        // Act
        List<InvoiceViewModel> result = service.findAllInvoices();

        // Assert
        assertFalse(result.isEmpty(), "Expected non-empty result");
        assertEquals(2,result.size());
    }

    @Test
    public void shouldValidateOrderRequest(){
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setCustomerName("Alice Johnson");
        invoice.setStreet("789 Pine St");
        invoice.setCity("Villageville");
        invoice.setState("VA");
        invoice.setZipcode("54321");
        invoice.setItemType("Game");
        invoice.setItemId(1);
        invoice.setQuantity(5);

        try {
            service.validateOrderRequest(invoice);
        } catch (Exception e) {
            fail("Exception thrown for valid input");
        }
    }
//    @Test
//    public void validateOrderRequestThrowsItemNotFound(){
//        InvoiceViewModel invoice = new InvoiceViewModel();
//        invoice.setCustomerName("Alice Johnson");
//        invoice.setStreet("789 Pine St");
//        invoice.setCity("Villageville");
//        invoice.setState("VA");
//        invoice.setZipcode("54321");
//        invoice.setItemType("Game");
//        invoice.setItemId(3);
//        invoice.setQuantity(5);
//
//        try {
//            service.validateOrderRequest(invoice);
//            fail("Expected NotFoundException to be thrown");
//        } catch (Exception e) {
//            assertTrue(e instanceof NotFoundException, "Expected NotFoundException but got " + e.getClass().getSimpleName());
//        }
//    }



    //  TShirt API Tests




    // Console API Tests




    // Game API Tests




    //  Helper
    private void setUpConsoleRepositoryMock() {
        consoleRepo = mock(ConsoleRepository.class);

        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("2");
        console.setManufacturer("Sony");
        console.setMemoryAmount("infinite");
        console.setProcessor("processor");
        console.setPrice(BigDecimal.valueOf(499.99));
        console.setQuantity(1);

        Console console2 = new Console();
        console2.setConsoleId(2);
        console2.setModel("2");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("infinite");
        console2.setProcessor("processor");
        console2.setPrice(BigDecimal.valueOf(499.99));
        console2.setQuantity(1);

        List<Console> aList = new ArrayList<>();
        aList.add(console);
        aList.add(console2);

        doReturn(console).when(consoleRepo).save(console);
        doReturn(console2).when(consoleRepo).save(console2);
        doReturn(Optional.of(console)).when(consoleRepo).findById(1);
        doReturn(Optional.of(console2)).when(consoleRepo).findById(2);
        doReturn(aList).when(consoleRepo).findAll();
        //doReturn(aList).when(consoleRepo).findByManufacturer(Integer.parseInt("Sony"));
    }
    private void setUpGameRepositoryMock() {
        gameRepo = mock(GameRepository.class);

        Game game = new Game();
        game.setId(1);
        game.setTitle("Assassin's Creed Valhalla");
        game.setEsrbRating("Mature");
        game.setDescription("Viking-themed Action RPG");
        game.setPrice(BigDecimal.valueOf(54.99));
        game.setStudio("Ubisoft");
        game.setQuantity(35);

        Game game2 = new Game();
        game2.setId(2);
        game2.setTitle("Assassin's Creed Valhalla");
        game2.setEsrbRating("Mature");
        game2.setDescription("Viking-themed Action RPG");
        game2.setPrice(BigDecimal.valueOf(54.99));
        game2.setStudio("Ubisoft");
        game2.setQuantity(35);

        List<Game> aList = new ArrayList<>();
        aList.add(game);
        aList.add(game2);

        List<Game> gamesWithTitle = new ArrayList<>();
        gamesWithTitle.add(game);
        doReturn(gamesWithTitle).when(gameRepo).findByTitle("Assassin's Creed Valhalla");
        doReturn(game).when(gameRepo).save(game);
        doReturn(game2).when(gameRepo).save(game2);
        doReturn(Optional.of(game)).when(gameRepo).findById(1);
        doReturn(Optional.of(game2)).when(gameRepo).findById(2);
        doReturn(aList).when(gameRepo).findAll();
        doReturn(aList).when(gameRepo).findByStudio("Ubisoft");
        doReturn(aList).when(gameRepo).findByEsrbRating("Mature");


    }
    private void setUpTshirtRepositoryMock() {
        tshirtRepo = mock(TShirtsRepository.class);

        TShirt tshirt = new TShirt();
        tshirt.setId(1);
        tshirt.setColor("Blue");
        tshirt.setDescription("Short sleeves");
        tshirt.setPrice(BigDecimal.valueOf(19.99));
        tshirt.setSize("Medium");
        tshirt.setQuantity(2);

        TShirt tshirt2 = new TShirt();
        tshirt2.setId(2);
        tshirt2.setColor("Blue");
        tshirt2.setDescription("Short sleeves");
        tshirt2.setPrice(BigDecimal.valueOf(19.99));
        tshirt2.setSize("Medium");
        tshirt2.setQuantity(2);

        List<TShirt> aList = new ArrayList<>();
        aList.add(tshirt);
        aList.add(tshirt2);

        doReturn(tshirt).when(tshirtRepo).save(tshirt);
        doReturn(tshirt2).when(tshirtRepo).save(tshirt2);
        doReturn(Optional.of(tshirt)).when(tshirtRepo).findById(1);
        doReturn(Optional.of(tshirt2)).when(tshirtRepo).findById(2);
        doReturn(aList).when(tshirtRepo).findAll();
        doReturn(aList).when(tshirtRepo).findByColor("Blue");
        doReturn(aList).when(tshirtRepo).findBySize("Medium");
    }
    private void setUpInvoiceRepositoryMock() {
        invoiceRepo = mock(InvoiceRepository.class);

        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setName("Alice Johnson");
        invoice.setStreet("789 Pine St");
        invoice.setCity("Villageville");
        invoice.setState("VA");
        invoice.setZipcode("54321");
        invoice.setItem_type("Game");
        invoice.setItem_id(1);
        invoice.setUnit_price(BigDecimal.valueOf(15.00));
        invoice.setQuantity(5);
        invoice.setTax(BigDecimal.valueOf(2.25));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.50));
        invoice.setSubtotal(BigDecimal.valueOf(75.00));
        invoice.setTotal(BigDecimal.valueOf(78.75));

        Invoice invoice2 = new Invoice();
        invoice2.setId(2);
        invoice2.setName("Alice Johnson");
        invoice2.setStreet("789 Pine St");
        invoice2.setCity("Villageville");
        invoice2.setState("VA");
        invoice2.setZipcode("54321");
        invoice2.setItem_type("Game");
        invoice2.setItem_id(1);
        invoice2.setUnit_price(BigDecimal.valueOf(15.00));
        invoice2.setQuantity(5);
        invoice2.setTax(BigDecimal.valueOf(2.25));
        invoice2.setProcessing_fee(BigDecimal.valueOf(1.50));
        invoice2.setSubtotal(BigDecimal.valueOf(75.00));
        invoice2.setTotal(BigDecimal.valueOf(78.75));

        List<Invoice> aList = new ArrayList<>();
        aList.add(invoice);
        aList.add(invoice2);

        doReturn(invoice).when(invoiceRepo).save(any());
        doReturn(invoice2).when(invoiceRepo).save(invoice2);
        doReturn(Optional.of(invoice)).when(invoiceRepo).findById(1);
        doReturn(Optional.of(invoice2)).when(invoiceRepo).findById(2);
        doReturn(aList).when(invoiceRepo).findAll();
    }
    private void setUpFeeRepositoryMock() {
        feeRepo = mock(FeeRepository.class);

        Fee fee = new Fee();
        fee.setFee(BigDecimal.valueOf(1.49));
        fee.setProductType("Console");

        Fee fee2 = new Fee();
        fee2.setFee(BigDecimal.valueOf(1.98));
        fee2.setProductType("Game");

        List<Fee> aList = new ArrayList<>();
        aList.add(fee);
        aList.add(fee2);

        doReturn(fee).when(feeRepo).save(fee);
        doReturn(fee2).when(feeRepo).save(fee2);
        doReturn(Optional.of(fee)).when(feeRepo).findById("Console");
        doReturn(Optional.of(fee2)).when(feeRepo).findById("Game");
        doReturn(aList).when(feeRepo).findAll();
    }
    private void setUpTaxRepositoryMock() {
        taxRepo = mock(TaxRepository.class);

        Tax tax = new Tax();
        tax.setState("VA");
        tax.setRate(BigDecimal.valueOf(0.55));

        Tax tax2 = new Tax();
        tax2.setState("WA");
        tax2.setRate(BigDecimal.valueOf(0.05));

        List<Tax> aList = new ArrayList<>();
        aList.add(tax);
        aList.add(tax2);

        doReturn(tax).when(taxRepo).save(tax);
        doReturn(tax2).when(taxRepo).save(tax2);
        doReturn(Optional.of(tax)).when(taxRepo).findById("VA");
        doReturn(Optional.of(tax2)).when(taxRepo).findById("WA");
        doReturn(aList).when(taxRepo).findAll();
    }
}
