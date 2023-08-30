package com.company.gamestore.service;

import com.company.gamestore.repositories.TShirtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.company.gamestore.repositories")
public class ServiceLayer {
    private TShirtsRepository tShirtsRepository;

    @Autowired
    public ServiceLayer(TShirtsRepository tShirtsRepository) {
        this.tShirtsRepository = tShirtsRepository;
    }
}
