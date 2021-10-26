package com.example.MG_RestaurantManager20.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    final private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts(){ return productRepository.findAll(); }

    @PostMapping
    public void addNewProduct(String productName, Double productMin, Double productQuantity, ProductUnit productUnit){
        Product product = new Product(productName, productMin, productQuantity, productUnit);
        productRepository.save(product);
    }


    @PutMapping(path = "{productId}")
    @Transactional
    public void updateProduct(Long productId, String productName, Double productMin, Double productQuantity, ProductUnit productUnit){
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new IllegalStateException("Product with this ID: '" + productId + "' doesn't exists.");
        });

        if(productName != null && productName.length() > 0 && !Objects.equals(product.getName(), productName)) {
            Optional<Product> productOptional = productRepository.findProductByName(productName);
            if(productOptional.isPresent()){
                throw new IllegalStateException("Product with this NAME: \"" + productName + "\" already exists.");
            }
            else {
                product.setName(productName);
            }
        }

        if(productMin != null && !Objects.equals(product.getMin(), productMin)) {
            product.setMin(productMin);
        }

        if(productQuantity != null && !Objects.equals(product.getQuantity(), productQuantity)) {
            product.setQuantity(productQuantity);
        }

        if(productUnit != null && !Objects.equals(product.getProductUnit(), productUnit)) {
            product.setProductUnit(productUnit);
        }
    }

    @DeleteMapping
    public void deleteProduct(Long id){ productRepository.deleteById(id); }

    @DeleteMapping
    public void deleteAllProducts() { productRepository.deleteAll(); }

}
