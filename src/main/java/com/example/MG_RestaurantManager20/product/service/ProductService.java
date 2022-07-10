package com.example.MG_RestaurantManager20.product.service;

import com.example.MG_RestaurantManager20.product.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    List<Product> getProducts();

    Optional<Product> getProduct(Long productId);

    Product addNewProduct(Product product);

    Product updateProduct(Long productId, Product product);

    void deleteProduct(Long productId);

    void deleteAllProducts();

}
