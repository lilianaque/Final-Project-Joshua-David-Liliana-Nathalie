package com.company.gamestore.controllers;

import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepo;

    //CREATE
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return gameRepo.save(game);
    }

    //READ

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGameById(@PathVariable int id) {
        Optional<Game> resultGame = gameRepo.findById(id);
        return resultGame.isPresent() ? resultGame.get() : null;
    }

    @GetMapping("/games/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByStudio(@PathVariable String studio) {
        return gameRepo.findByStudio(studio);
    }

    @GetMapping("/games/ersb/{ersbRating}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByErsbRating(@PathVariable String ersbRating) {
        return gameRepo.findByEsrbRating(ersbRating);
    }
    @GetMapping("/games/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByTitle(@PathVariable String title) {
        return gameRepo.findByTitle(title);
    }

    //UPDATE
    @PutMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGameById(@PathVariable int id, @RequestBody Game game) {
        gameRepo.save(game);
    }

    //Delete
    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameById(@PathVariable int id) {
        Optional<Game> resultGame = gameRepo.findById(id);
        if (resultGame.isPresent()) { gameRepo.deleteById(id); }
    }
}