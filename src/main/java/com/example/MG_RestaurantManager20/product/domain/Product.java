package com.example.MG_RestaurantManager20.product.domain;

import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double min;
    private Double quantity;
    private ProductUnit productUnit;
    private Double calories;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = RequiredProducts.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<RequiredProducts> requiredProducts;

    public Product(String name, Double min, Double quantity, ProductUnit productUnit, Double calories) {
        this.name = name;
        this.min = min;
        this.quantity = quantity;
        this.productUnit = productUnit;
        this.calories = calories;
    }

    public Product(Object object) {
    }

    @Override
    public String toString() {
        return name;
    }
}
