package com.company.gamestore.controllers;

import com.company.gamestore.models.Invoice;
import com.company.gamestore.models.InvoiceViewModel;
import com.company.gamestore.repositories.InvoiceRepository;
import com.company.gamestore.service.ServiceLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    ServiceLayer serviceLayer;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice createInvoice(@RequestBody @Valid InvoiceViewModel ivm) {
        return invoiceRepo.save(serviceLayer.createInvoice(ivm));
    }

    // Read an Invoice by ID
    @GetMapping("/{invoice_id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable Integer invoice_id) {
        return invoiceRepo.findById(invoice_id).orElse(null);
    }

    // Read all Invoices
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }

    // Find Invoices by Customer Name
    @GetMapping("/customer/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoicesByCustomerName(@PathVariable String name) {
        return invoiceRepo.findByName(name);
    }

    // Update an Invoice by ID


    // Delete an Invoice by ID
    @DeleteMapping("/{invoice_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Integer invoice_id) {
        invoiceRepo.deleteById(invoice_id);
    }
}