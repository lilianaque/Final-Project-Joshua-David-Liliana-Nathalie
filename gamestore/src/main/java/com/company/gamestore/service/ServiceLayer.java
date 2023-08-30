package com.company.gamestore.service;

import com.company.gamestore.models.TShirt;
import com.company.gamestore.repositories.TShirtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ComponentScan("com.company.gamestore.repositories")
public class ServiceLayer {
    private TShirtsRepository tShirtsRepository;

    @Autowired
    public ServiceLayer(TShirtsRepository tShirtsRepository) {
        this.tShirtsRepository = tShirtsRepository;
    }

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


}

