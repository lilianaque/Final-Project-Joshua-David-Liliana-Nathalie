package com.company.gamestore.controllers;
import com.company.gamestore.service.ServiceLayer;
import com.company.gamestore.models.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    //  POST route creates an invoice
    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel invoice) {
        return serviceLayer.saveInvoice(invoice);
    }

    // GET route retrieves invoice by id
    @GetMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoiceById(@PathVariable Integer id) {
        return serviceLayer.findInvoiceById(id);
    }

    // GET route retrieves all invoices
    @GetMapping("/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {
        return serviceLayer.findAllInvoices();
    }

    // GET route retrieves invoice by name
    @GetMapping("/invoices/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoiceByName(@PathVariable String name) {
        return serviceLayer.findInvoiceByName(name);
    }
}