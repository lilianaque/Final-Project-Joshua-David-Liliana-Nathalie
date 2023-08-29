package com.company.gamestore.controllers;

import com.company.gamestore.models.Game;
import com.company.gamestore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class graphQLController {
    @Autowired
    GameRepository gameRepo;


    //---------------GAMES-------------------//
    @QueryMapping
    public List<Game> games(){
        return gameRepo.findAll();
    }

    @QueryMapping
    public Game gameById(@Argument int id){
        Optional<Game> returnVal = gameRepo.findById(id);
        return returnVal.isPresent() ? returnVal.get() : null;
    }

    @QueryMapping
    public List<Game> gamesByStudio(@Argument String studio){
        return gameRepo.findByStudio(studio);
    }

    @QueryMapping
    public List<Game> gamesByErsbRating(@Argument String ersbRating){
        return gameRepo.findByEsrbRating(ersbRating);
    }

    @QueryMapping
    public List<Game> gamesByTitle(@Argument String title){
        return gameRepo.findByTitle(title);
    }


}
