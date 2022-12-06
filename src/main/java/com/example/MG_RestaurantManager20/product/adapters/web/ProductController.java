package com.example.MG_RestaurantManager20.product.adapters.web;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{productId}")
    public Optional<Product> getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/product/name/{productName}")
    public Optional<Product> getProductByName(@PathVariable("productName") String productName) {
        return productService.getProductByName(productName);
    }

    @PostMapping("products/add")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @PutMapping("products/update/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId,
                                 @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("products/delete/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @DeleteMapping("products/delete/all")
    public void deleteAllProducts() {
        productService.deleteAllProducts();
    }
}
