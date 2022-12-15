package com.example.MG_RestaurantManager20.recipe2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Recipe2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user_fk;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = RequiredProducts.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_fk", referencedColumnName = "id")
    private List<RequiredProducts> requiredProducts;

    public Recipe2(Long user_fk, String name, String description) {
        this.user_fk = user_fk;
        this.name = name;
        this.description = description;
    }

    public Recipe2(String name, String description, List<RequiredProducts> requiredProducts) {
        this.name = name;
        this.description = description;
        this.requiredProducts = requiredProducts;
    }

}
