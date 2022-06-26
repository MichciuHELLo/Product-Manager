package com.example.MG_RestaurantManager20.recipe.domain;

import com.example.MG_RestaurantManager20.product.domain.Product;
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
    private Double calories;

    public Recipe(String name, String description, Double calories)
    {
        this.name = name;
        this.description = description;
        this.requiredProducts = "";
        this.calories = calories;
    }

    @Override
    public String toString() {
        return name;
    }
}
