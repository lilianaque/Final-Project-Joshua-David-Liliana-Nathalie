package com.company.gamestore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "fee")
public class Fee {

    @Id
    @Column(name = "product_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productType;

    @NotNull
    @Column(name = "fee", precision = 8,scale = 2)
    private BigDecimal fee;

    public Fee() {
    }

    public Fee(String productType, BigDecimal fee) {
        this.productType = productType;
        this.fee = fee;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}