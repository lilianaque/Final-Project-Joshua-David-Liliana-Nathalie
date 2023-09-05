package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "console")
public class Console {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "console_id")
    private int consoleID;

    @NotNull
    private String manufacturer;
    @NotNull
    private String model;

    @Column(name = "memory_amount")
    @NotNull
    private String memoryAmount;

    @Size(max = 20)
    @NotNull
    private String processor;

    @Digits(integer = 5, fraction = 2)
    @NotNull
    private BigDecimal price;

    @NotNull
    private int quantity;


    public Integer getConsoleId() {
        return consoleID;
    }

    public void setConsoleId(int consoleID) {
        this.consoleID = consoleID;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMemoryAmount() {
        return memoryAmount;
    }

    public void setMemoryAmount(String memoryAmount) {
        this.memoryAmount = memoryAmount;
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
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}
