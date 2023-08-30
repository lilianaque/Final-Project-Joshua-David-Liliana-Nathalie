package com.company.gamestore.controllers;

import com.company.gamestore.models.TShirt;
import com.company.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ComponentScan("com.company.gamestore.service")
public class TShirtController {

    @Autowired
    ServiceLayer serviceLayer;

    @PostMapping("/t_shirts")
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt createTShirt(@RequestBody TShirt tShirt) {
        return serviceLayer.saveTShirt(tShirt);
    }

    @PutMapping("/t_shirts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@RequestBody TShirt tShirt) {
        serviceLayer.updateTShirt(tShirt);
    }

    @DeleteMapping("/t_shirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable int id) {
        serviceLayer.deleteTShirt(id);
    }

    @GetMapping("/t_shirts/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirt getTShirtById(@PathVariable int id) {
        return serviceLayer.findTShirt(id);
    }

    @GetMapping("/t_shirts")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getAllTShirts() {
        return  serviceLayer.findAllTShirts();
    }

    @GetMapping("/t_shirts/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getTShirtByColor(String color) {
        return serviceLayer.findTShirtByColor(color);
    }

    @GetMapping("/t_shirts/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getTShirtBySize(String size) {
        return serviceLayer.findTShirtBySize(size);
    }






}
