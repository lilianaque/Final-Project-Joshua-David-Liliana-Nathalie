package com.company.gamestore.repositories;

import com.company.gamestore.models.Console;
import com.company.gamestore.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository  extends JpaRepository<Invoice, Integer> {
    List<Invoice> findInvoiceByName(String name);

}
