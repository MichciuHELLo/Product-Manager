package com.example.MG_RestaurantManager20.product.service.ports.incoming;

import com.example.MG_RestaurantManager20.product.domain.ProductUnit;

import javax.validation.constraints.NotBlank;

public class ProductCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Double min;

    @NotBlank
    private Double quantity;
    
    @NotBlank
    private ProductUnit productUnit;

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

    public ProductCreateRequest(String name, Double min, Double quantity, ProductUnit productUnit) {
        this.name = name;
        this.min = min;
        this.quantity = quantity;
        this.productUnit = productUnit;
    }
}
