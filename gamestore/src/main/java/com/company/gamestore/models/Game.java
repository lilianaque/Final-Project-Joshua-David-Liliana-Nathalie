package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "game")
public class Game {

    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please supply a value for title")
    @Max(value = 50, message = "Title must be less than 50 characters")
    private String title;

    @Column(name = "esrb_rating")
    @NotEmpty(message = "Please supply a value")
    @Max(value = 50, message = "Title must be less than 50 characters")
    private String esrbRating;

    @NotEmpty(message = "Please supply a value")
    @Max(value = 255, message = "Title must be less than 50 characters")
    private String description;
    @NotEmpty(message = "Please supply a value")
    @Column(precision = 5,scale = 2) // check this with TA
    private BigDecimal price; //must be between 5 and 2

    @NotEmpty(message = "Please supply a value")
    @Max(value = 50, message = "Must be less than 50 characters")
    private String studio;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && quantity == game.quantity && Objects.equals(title, game.title) && Objects.equals(esrbRating, game.esrbRating) && Objects.equals(description, game.description) && Objects.equals(price, game.price) && Objects.equals(studio, game.studio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, esrbRating, description, price, studio, quantity);
    }
}
