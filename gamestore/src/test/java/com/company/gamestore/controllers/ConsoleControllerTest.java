package com.company.gamestore.controllers;

import com.company.gamestore.models.Console;
import com.company.gamestore.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ConsoleRepository consoleRepository;

    @MockBean
    GameRepository gameRepository;

    @MockBean
    InvoiceRepository invoiceRepository;
    @MockBean
    TShirtsRepository tShirtsRepository;
    @MockBean
    FeeRepository feeRepository;

    @MockBean
    TaxRepository taxRepository;
    private Console console;

    @BeforeEach
    public void setUp() {
    }

    @Autowired
    private ObjectMapper mapper;

    // Create
    @Test
    public void shouldCreateConsole() throws Exception {
        Console console = new Console();
        console.setManufacturer("Nintendo");
        console.setModel("PS4");
        console.setMemoryAmount("8GB");
        console.setPrice(new BigDecimal("399.99"));
        console.setQuantity(5);

        String inputJson = mapper.writeValueAsString(console);

        mockMvc.perform(
                        post("/consoles")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    // Read
    @Test
    public void shouldGetAllConsoles() throws Exception {
        mockMvc.perform(
                        get("/consoles")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetConsoleById() throws Exception {
        when(consoleRepository.findById(1)).thenReturn(java.util.Optional.of(new Console()));

        mockMvc.perform(
                        get("/consoles/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    // Update
    @Test
    public void shouldUpdateConsole() throws Exception {
        Console console = new Console();
        console.setManufacturer("Microsoft");
        console.setModel("PS5");
        console.setMemoryAmount("2TB");
        console.setPrice(new BigDecimal("699.99"));
        console.setQuantity(10);

        String inputJson = mapper.writeValueAsString(console);

        mockMvc.perform(
                        put("/consoles")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    // Delete
    @Test
    public void shouldDeleteConsole() throws Exception {
        mockMvc.perform(
                        delete("/consoles/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetConsolesByManufacturer() throws Exception {
        mockMvc.perform(get("/consoles/manufacturer/Microsoft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].consoleId").value(console.getConsoleId()))
                .andExpect(jsonPath("$[0].manufacturer").value(console.getManufacturer()));
    }

}

