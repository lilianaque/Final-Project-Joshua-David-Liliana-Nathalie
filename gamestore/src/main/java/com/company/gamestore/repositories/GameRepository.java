package com.company.gamestore.repositories;

import com.company.gamestore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByStudio(String studio);
    List<Game> findByErsbRating(String ersbRating);
    List<Game> findByTitle(String title);
}
