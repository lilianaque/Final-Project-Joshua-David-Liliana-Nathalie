package com.company.gamestore.repositories;

import com.company.gamestore.models.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InvoiceRepositoryTests {

    @Autowired
    InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setUp() throws Exception{
        invoiceRepository.deleteAll();
    }

    @Test
    public void shouldCreateInvoice() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));


        Invoice invoice1 = invoiceRepository.save(invoice);



        assertEquals(invoice.getCity(), invoice1.getCity());

    }

    @Test
    public void shouldGetInvoiceById() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));

        Invoice invoice2 = new Invoice();
        invoice2.setName("Jane");
        invoice2.setStreet("123 ave");
        invoice2.setCity("New York");
        invoice2.setState("NY");
        invoice2.setZipcode("10001");
        invoice2.setItem_type("console");
        invoice2.setItem_id(2);
        invoice2.setUnit_price(BigDecimal.valueOf(10.00));
        invoice2.setQuantity(2);
        invoice2.setTax(BigDecimal.valueOf(1.20));
        invoice2.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice2.setSubtotal(BigDecimal.valueOf(20.00));
        invoice2.setTotal(BigDecimal.valueOf(23.19));

        invoice = invoiceRepository.save(invoice);
        invoice2 = invoiceRepository.save(invoice2);

        Invoice invoiceGot = invoiceRepository.findById(invoice.getId()).orElse(null);

        assertNotNull(invoiceGot);
        assertEquals("New York", invoiceGot.getCity());
        assertEquals("console", invoiceGot.getItem_type());
        assertEquals("NY", invoiceGot.getState());
        assertEquals(2, invoiceGot.getQuantity());

    }

    @Test
    public void shouldGetAllInvoices() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);

        Invoice invoice2 = new Invoice();
        invoice2.setName("Jane");
        invoice2.setStreet("123 ave");
        invoice2.setCity("New York");
        invoice2.setState("NY");
        invoice2.setZipcode("10001");
        invoice2.setItem_type("console");
        invoice2.setItem_id(2);
        invoice2.setUnit_price(BigDecimal.valueOf(10.00));
        invoice2.setQuantity(2);
        invoice2.setTax(BigDecimal.valueOf(1.20));
        invoice2.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice2.setSubtotal(BigDecimal.valueOf(20.00));
        invoice2.setTotal(BigDecimal.valueOf(23.19));


        invoiceRepository.save(invoice2);

        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertEquals(2, invoiceList.size());

    }

    @Test
    public void shouldUpdateInvoice() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);

        //Update name
        invoice.setName("Jane");
        invoiceRepository.save(invoice);

        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getId());

        assertTrue(invoice1.isPresent());
        assertEquals("Jane", invoice1.get().getName());
        assertEquals(invoice.getStreet(), invoice1.get().getStreet());
        assertEquals(invoice.getCity(), invoice1.get().getCity());
    }

    @Test
    public void shouldDeleteInvoice() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);

        invoiceRepository.delete(invoice);

        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getId());

        assertFalse(invoice1.isPresent());
    }

    @Test
    public void shouldGetInvoiceByName() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItem_type("console");
        invoice.setItem_id(3);
        invoice.setUnit_price(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessing_fee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);


        List<Invoice> invoices = invoiceRepository.findByName(invoice.getName());

        assertEquals(invoices.size(), 1);
    }

}