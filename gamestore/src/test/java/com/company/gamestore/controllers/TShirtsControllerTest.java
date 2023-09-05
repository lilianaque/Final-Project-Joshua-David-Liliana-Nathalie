package com.company.gamestore.controllers;

import com.company.gamestore.models.TShirt;
import com.company.gamestore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TShirtControllerTest {

    @InjectMocks
    TShirtController controller;

    @Mock
    ServiceLayer serviceLayer;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createTShirt() throws Exception {
        TShirt inputTShirt = new TShirt();
        inputTShirt.setColor("Red");
        inputTShirt.setSize("M");

        TShirt outputTShirt = new TShirt();
        outputTShirt.setId(1);
        outputTShirt.setColor("Red");
        outputTShirt.setSize("M");

        when(serviceLayer.saveTShirt(inputTShirt)).thenReturn(outputTShirt);

        mockMvc.perform(
                        post("/t_shirts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(inputTShirt)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(outputTShirt)));
    }

    @Test
    void updateTShirt() throws Exception {
        TShirt tShirtToUpdate = new TShirt();
        tShirtToUpdate.setId(1);
        tShirtToUpdate.setColor("Blue");
        tShirtToUpdate.setSize("L");

        mockMvc.perform(
                        put("/t_shirts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(tShirtToUpdate)))
                .andExpect(status().isNoContent());

        verify(serviceLayer, times(1)).updateTShirt(tShirtToUpdate);
    }

    @Test
    void deleteTShirt() throws Exception {
        mockMvc.perform(delete("/t_shirts/1"))
                .andExpect(status().isNoContent());

        verify(serviceLayer, times(1)).deleteTShirt(1);
    }

    @Test
    void getTShirtById() throws Exception {
        TShirt tShirt = new TShirt();
        tShirt.setId(1);
        tShirt.setColor("Red");
        tShirt.setSize("M");

        when(serviceLayer.findTShirt(1)).thenReturn(tShirt);

        mockMvc.perform(get("/t_shirts/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tShirt)));
    }

    @Test
    void getAllTShirts() throws Exception {
        TShirt tShirt1 = new TShirt();
        tShirt1.setId(1);
        tShirt1.setColor("Red");
        tShirt1.setSize("M");

        TShirt tShirt2 = new TShirt();
        tShirt2.setId(2);
        tShirt2.setColor("Blue");
        tShirt2.setSize("L");

        List<TShirt> tShirtList = Arrays.asList(tShirt1, tShirt2);

        when(serviceLayer.findAllTShirts()).thenReturn(tShirtList);

        mockMvc.perform(get("/t_shirts"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tShirtList)));
    }

    @Test
    void getTShirtByColor() throws Exception {
        TShirt tShirt = new TShirt();
        tShirt.setId(1);
        tShirt.setColor("Red");
        tShirt.setSize("M");

        List<TShirt> tShirtList = Arrays.asList(tShirt);

        when(serviceLayer.findTShirtByColor("Red")).thenReturn(tShirtList);

        mockMvc.perform(get("/t_shirts/color/Red"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tShirtList)));
    }

    @Test
    void getTShirtBySize() throws Exception {
        TShirt tShirt = new TShirt();
        tShirt.setId(1);
        tShirt.setColor("Red");
        tShirt.setSize("M");

        List<TShirt> tShirtList = Arrays.asList(tShirt);

        when(serviceLayer.findTShirtBySize("M")).thenReturn(tShirtList);

        mockMvc.perform(get("/t_shirts/size/M"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(tShirtList)));
    }
}
