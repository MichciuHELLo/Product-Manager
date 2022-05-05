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
    // private Map<String, String> productsWithQuantity;
    private String description;



    public Recipe(String name, String description)
    {
        this.name = name;
        this.description = description;
    }


}
