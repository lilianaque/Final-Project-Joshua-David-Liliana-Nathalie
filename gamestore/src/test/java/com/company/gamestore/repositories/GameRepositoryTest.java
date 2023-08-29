package com.company.gamestore.repositories;

import com.company.gamestore.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    public void setUp() throws Exception {
        gameRepository.deleteAll();
    }
    //-----------Create----------------
    @Test
    public void shouldCreateGame() {
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        assertThat(game.getId()).isNotNull();
    }

    //-----------Read +----------------
    @Test
    public void shouldGetGameById() {
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        Game gameFromDB = gameRepository.findById(game.getId()).orElse(null);
        assertThat(gameFromDB).isNotNull();
        assertThat(gameFromDB.getTitle()).isEqualTo("Call of Duty");
    }
    @Test
    public void shouldGetGameByTitle(){
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        List<Game> gameFromDB = gameRepository.findByTitle(game.getTitle());

        assertThat(gameFromDB).isNotNull();
        Game testGame = gameFromDB.get(0);
        assertThat(testGame.getTitle()).isEqualTo("Call of Duty");
    }
    @Test
    public void shouldGetGameByEsrbRating(){
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        List<Game> gameFromDB = gameRepository.findByEsrbRating(game.getEsrbRating());

        assertThat(gameFromDB).isNotNull();
        Game testGame = gameFromDB.get(0);
        assertThat(testGame.getEsrbRating()).isEqualTo("M");
    }

    @Test
    public void shouldGetGameByStudio(){
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        List<Game> gameFromDB = gameRepository.findByStudio(game.getStudio());

        assertThat(gameFromDB).isNotNull();
        Game testGame = gameFromDB.get(0);
        assertThat(testGame.getStudio()).isEqualTo("1");
    }

    //-----------Update----------------

    @Test
    public void shouldUpdateGame(){
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        game.setTitle("Call of Duty 2");
        gameRepository.save(game);
        Game gameFromDB = gameRepository.findById(game.getId()).orElse(null);
        assertThat(gameFromDB).isNotNull();
        assertThat(gameFromDB.getTitle()).isEqualTo("Call of Duty 2");
    }

    //-----------Delete----------------
    @Test
    public void shouldDeleteGame(){
        Game game = new Game(
                1,
                "Call of Duty",
                "M",
                "FPS",
                new BigDecimal(100),
                "1",
                2
        );
        game = gameRepository.save(game);
        gameRepository.deleteById(game.getId());
        Game gameFromDB = gameRepository.findById(game.getId()).orElse(null);
        assertThat(gameFromDB).isNull();
    }

}
