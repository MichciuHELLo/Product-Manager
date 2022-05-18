package com.example.MG_RestaurantManager20.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  @Transient
    private Long id;

    private String name;
    private Double min;
    private Double quantity;
    private ProductUnit productUnit;

    public Product(String name, Double min, Double quantity, ProductUnit productUnit) {
        this.name = name;
        this.min = min;
        this.quantity = quantity;
        this.productUnit = productUnit;
    }

    public Product(Object object) {
    }

    @Override
    public String toString() {
        return name;
    }
}
