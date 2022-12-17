package com.example.MG_RestaurantManager20.product.service;

import com.example.MG_RestaurantManager20.product.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface ProductService {

    List<Product> getProducts();

    Product getProductById(Long productId);

    Optional<Product> getProductByNameAndUserSessionId(String name, Long userId);

    List<Product> getProductsByUserSessionId(Long userId);

    Product addNewProduct(Product product);

    Product updateProduct(Long productId, Product product);

    void deleteProduct(Long productId);

    void deleteProducts(Set<Product> employees);

    void deleteAllProducts();

}
