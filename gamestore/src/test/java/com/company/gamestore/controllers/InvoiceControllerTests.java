package com.company.gamestore.controllers;

import com.company.gamestore.models.Invoice;
import com.company.gamestore.repositories.InvoiceRepository;
import com.company.gamestore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTests {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepository invoiceRepository;
    @MockBean
    private ServiceLayer serviceLayer;


    @Test
    public void shouldAddInvoice() throws Exception {

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

        String inputJson = objectMapper.writeValueAsString(invoice);

        mockMvc.perform(
                        post("/invoices")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldGetInvoiceById() throws Exception {

        mockMvc.perform(
                        get("/invoices/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void shouldGetAllInvoices() throws  Exception {

        mockMvc.perform(
                        get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void shouldGetInvoiceByName() throws Exception {

        mockMvc.perform(
                        get("/invoices/customer/John"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}