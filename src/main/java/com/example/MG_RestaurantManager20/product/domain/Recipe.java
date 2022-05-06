package com.example.MG_RestaurantManager20.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.beans.Transient;
import java.util.List;
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


    // private List<Product> products;
    @ElementCollection
    private Map<Product, Integer> productsWithQuantity;
    private String description;
    private String recipeDescription;


    public Recipe(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.recipeDescription = "";
    }

    @Override
    public String toString() {
        return name;
    }
}
