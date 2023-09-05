package com.company.gamestore.controllers;

import com.company.gamestore.models.Console;
import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.ConsoleRepository;
import com.company.gamestore.repositories.GameRepository;
import com.company.gamestore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consoles")
public class ConsoleController {

    @Autowired
    ServiceLayer serviceLayer;

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody Console console) {
        return serviceLayer.saveConsole(console);
    }

    //READ ALL
    @GetMapping("/consoles")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles() {
        return serviceLayer.findAllConsoles();
    }

    //READ BY ID
    @GetMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsoleById(@PathVariable Integer id) {
        Optional<Console> returnVal = Optional.ofNullable(serviceLayer.findConsoleById(id));
        if(returnVal.isPresent()){
            return returnVal.get();
        }
        else{
            return null;
        }
    }

    //UPDATE
    @PutMapping("/consoles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody Console console){
        serviceLayer.updateConsole(console);
    }

    //DELETE
    @DeleteMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id){
        serviceLayer.deleteConsole(id);
    }

    //BY MANUFACTURER
    @GetMapping("/consoles/manufacturer/{manufacturerId}")
    public List<Console> getConsolesByManufacturerId(@PathVariable int manufacturerId) {
        return serviceLayer.findConsoleByManufacturer(manufacturerId);
    }



}
