package com.company.gamestore.controllers;

import com.company.gamestore.models.Console;
import com.company.gamestore.repositories.ConsoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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


    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ConsoleRepository consoleRepository;


    @BeforeEach
    public void setUp() {
    }

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

        Console console1 = new Console();
        console1.setConsoleId(1);
        console1.setManufacturer("Nintendo");
        console1.setModel("PS4");
        console1.setMemoryAmount("8GB");
        console1.setPrice(new BigDecimal("399.99"));
        console1.setQuantity(5);

        Console console2 = new Console();
        console2.setConsoleId(1);
        console2.setManufacturer("Nintendo");
        console2.setModel("PS4");
        console2.setMemoryAmount("8GB");
        console2.setPrice(new BigDecimal("399.99"));
        console2.setQuantity(5);


        mockMvc.perform(
                        get("/consoles")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
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

//    @Test
//    public void shouldGetConsolesByManufacturer() throws Exception {
//        mockMvc.perform(get("/consoles/manufacturer/Microsoft"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].consoleId").value(console.getConsoleId()))
//                .andExpect(jsonPath("$[0].manufacturer").value(console.getManufacturer()));
//    }

}