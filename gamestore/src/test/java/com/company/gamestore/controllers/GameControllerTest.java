package com.company.gamestore.controllers;

import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
    }

    // Create
    @Test
    public void shouldCreateGame() throws Exception {
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );

        String inputJson = mapper.writeValueAsString(game);

        mockMvc.perform(
                post("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    //Read
    @Test
    public void shouldGetAllGames() throws Exception {
        //Arrange
        Game game1 = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        Game game2 = new Game(
                2,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );

        //Act
        mockMvc.perform(
                get("/games")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetGamesByStudio() throws Exception {
        //Arrange
        Game game1 = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        Game game2 = new Game(
                2,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );

        //Act
        mockMvc.perform(
                get("/games/studio/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetGamesByTitle() throws Exception{
        //Arrange
        Game game1 = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        Game game2 = new Game(
                2,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        mockMvc.perform(
                get("/games/title/Call of Duty")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetGamesByEsrb() throws Exception{
        //Arrange
        Game game1 = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        Game game2 = new Game(
                2,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        mockMvc.perform(
                get("/games/esrb/M")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Update
    @Test
    public void shouldUpdateGame() throws Exception {
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );

        String inputJson = mapper.writeValueAsString(game);
        mockMvc.perform(
                put("/games/")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    public void shouldDeleteGame() throws Exception {
        mockMvc.perform(
                delete("/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }






}
