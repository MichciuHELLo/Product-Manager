package com.example.MG_RestaurantManager20.product;

import javax.persistence.*;

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

    public Product() {
    }

    public Product(String name, Double min, Double quantity, ProductUnit productUnit) {
        this.name = name;
        this.min = min;
        this.quantity = quantity;
        this.productUnit = productUnit;
    }

    public Product(Object object) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", min=" + min +
                ", quantity=" + quantity +
                ", productUnit=" + productUnit +
                '}';
    }
}
