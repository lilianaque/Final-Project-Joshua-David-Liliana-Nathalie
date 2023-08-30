package com.company.gamestore.repositories;

import com.company.gamestore.models.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TShirtsRepository extends JpaRepository<TShirt, Integer> {
    List<TShirt> findByColor(String color);
    List<TShirt> findBySize(String size);
}
