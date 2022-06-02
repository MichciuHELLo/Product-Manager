package com.example.MG_RestaurantManager20.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    @ElementCollection
    private Map<Product, Integer> productsWithQuantity;
    private String description;
    private String requiredProducts;


    public Recipe(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.requiredProducts = "";
    }

    @Override
    public String toString() {
        return name;
    }
}
