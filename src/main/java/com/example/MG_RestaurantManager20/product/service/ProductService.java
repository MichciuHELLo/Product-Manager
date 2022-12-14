package com.example.MG_RestaurantManager20.product.service;

import com.example.MG_RestaurantManager20.product.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Long productId);

    Product addNewProduct(Product product);

    Product updateProduct(Long productId, Product product);

    void deleteProduct(Long productId);

    void deleteAllProducts();

}
