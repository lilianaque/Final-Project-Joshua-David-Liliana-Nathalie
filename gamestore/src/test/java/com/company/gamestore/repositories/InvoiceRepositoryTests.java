package com.company.gamestore.repositories;

import com.company.gamestore.models.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));


        invoiceRepository.save(invoice);

        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getId());

        assertEquals(invoice1.get(), invoice);
    }

    @Test
    public void shouldGetInvoiceById() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItemType("console");
        invoice.setItemId(4);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));


        invoiceRepository.save(invoice);

        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getId());

        assertEquals(invoice1.get(), invoice);
    }

    @Test
    public void shouldGetAllInvoices() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);

        Invoice invoice2 = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice2);

        List<Invoice> invoices = invoiceRepository.findAll();

        assertEquals(invoices.size(), 2);
    }

    @Test
    public void shouldUpdateInvoice() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);

        invoice.setName("Jane");
        invoiceRepository.save(invoice);

        Optional<Invoice> invoice1 = invoiceRepository.findById(invoice.getId());

        assertEquals(invoice1.get(), invoice);
    }

    @Test
    public void shouldDeleteInvoice() {

        Invoice invoice = new Invoice();
        invoice.setName("John");
        invoice.setStreet("123 ave");
        invoice.setCity("New York");
        invoice.setState("NY");
        invoice.setZipcode("10001");
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
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
        invoice.setItemType("console");
        invoice.setItemId(3);
        invoice.setUnitPrice(BigDecimal.valueOf(10.00));
        invoice.setQuantity(2);
        invoice.setTax(BigDecimal.valueOf(1.20));
        invoice.setProcessingFee(BigDecimal.valueOf(1.99));
        invoice.setSubtotal(BigDecimal.valueOf(20.00));
        invoice.setTotal(BigDecimal.valueOf(23.19));
        invoiceRepository.save(invoice);


        List<Invoice> invoices = invoiceRepository.findInvoiceByName(invoice.getName());

        assertEquals(invoices.size(), 1);
    }

}