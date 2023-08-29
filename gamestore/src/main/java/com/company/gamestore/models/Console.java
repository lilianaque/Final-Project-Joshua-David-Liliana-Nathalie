package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "console")
public class Console {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int consoleID;
    private String manufacturer;


    public Integer getConsoleId() {
        return consoleID;
    }

    public void setConsoleId(Integer consoleID) {
        this.consoleID = consoleID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
