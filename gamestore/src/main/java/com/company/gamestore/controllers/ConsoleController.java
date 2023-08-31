package com.company.gamestore.controllers;

import com.company.gamestore.models.Console;

import com.company.gamestore.repositories.ConsoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/consoles")
public class ConsoleController {

    @Autowired
    ConsoleRepository consoleRepository;

    //CREATE
    @PostMapping("/consoles")
    @ResponseStatus(HttpStatus.CREATED)
    public Console createConsole(@RequestBody Console console) {
        return consoleRepository.save(console);
    }

    //READ ALL
    @GetMapping("/consoles")
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles() {
        return consoleRepository.findAll();
    }

    //READ BY ID
    @GetMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsoleById(@PathVariable Integer id) {
        Optional<Console> returnVal = consoleRepository.findById(id);
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
        consoleRepository.save(console);
    }

    //DELETE
    @DeleteMapping("/consoles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id){
        consoleRepository.deleteById(id);
    }

    //BY MANUFACTURER
    @GetMapping("/consoles/manufacturer/{manufacturerId}")
    public List<Console> getBooksByManufacturerId(@PathVariable int manufacturerId) {
        return consoleRepository.findByManufacturer(manufacturerId);
    }



}
