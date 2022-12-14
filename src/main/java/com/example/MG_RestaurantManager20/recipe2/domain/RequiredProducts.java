package com.example.MG_RestaurantManager20.recipe2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class RequiredProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requiredProductId;

    private Long recipe_fk;

    private Long product_fk;

    private String name;

    private Double quantity;

    public RequiredProducts(Long recipe_fk, Long product_fk, Double quantity) {
        this.recipe_fk = recipe_fk;
        this.product_fk = product_fk;
        this.quantity = quantity;
    }
}
