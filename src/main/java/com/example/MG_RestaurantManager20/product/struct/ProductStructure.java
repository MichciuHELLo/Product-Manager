package com.example.MG_RestaurantManager20.product.struct;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductStructure {

    public Product product;
    public Double productQuantity;
    public ProductUnit productUnit;

    @Override
    public String toString() {
        return product + " " + productQuantity + " " + productUnit + "~\n";
    }
}
