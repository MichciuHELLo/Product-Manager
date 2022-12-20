package com.example.MG_RestaurantManager20.product.domain;

import com.example.MG_RestaurantManager20.recipe.domain.RequiredProducts;
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

    private Long user_fk;

    private String name;
    private Double min;
    private Double quantity;
    private ProductUnit productUnit;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = RequiredProducts.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_fk", referencedColumnName = "id")
    private List<RequiredProducts> requiredProducts;

    public Product(Long user_fk, String name, Double min, Double quantity, ProductUnit productUnit) {
        this.user_fk = user_fk;
        this.name = name;
        this.min = min;
        this.quantity = quantity;
        this.productUnit = productUnit;
    }

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
