package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "TSHIRT")
public class TShirt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TShirt_id")
    private int id;

    @NotEmpty(message = "You must provide a size for shirt")
    @Size(max = 20, message = "Supply value must be less than 20 characters")
    private String size;

    @NotEmpty(message = "You must provide a color for shirt")
    @Size(max = 20, message = "Supply value must be less than 20 characters")
    private String color;

    @NotEmpty(message = "You must provide a value for description")
    @Size(max = 20, message = "Supply value must be less than 20 characters")
    private String description;

    @Column(precision = 5, scale = 2)
    private BigDecimal price;

    @Min(value=0, message = "Quantity cannot be negative")
    private int quantity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        TShirt tShirt = (TShirt) o;
        return id == tShirt.id && Objects.equals(size, tShirt.size) && Objects.equals(color, tShirt.color) && Objects.equals(description, tShirt.description) && Objects.equals(price, tShirt.price) && quantity == tShirt.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "Tshirt{" +
                "; Id: " + id +
                "; Size: " + size + '\'' +
                "; Color: " + color + '\'' +
                "; Description: " + description + '\'' +
                "; Price: " + price + '\'' +
                "; Quantity: " + quantity + "}";
    }
}