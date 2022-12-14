package com.example.MG_RestaurantManager20.product.service.impl;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.getById(productId);
    }

    @Override
    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, Product product) {
        Product oldProduct = productRepository.findById(productId).orElseThrow(() -> {
            throw new IllegalStateException("Product with this ID: '" + productId + "' doesn't exists.");
        });

        if (product.getName() != null && product.getName().length() > 0 && !Objects.equals(product.getName(), oldProduct.getName())) {
            Optional<Product> productOptional = productRepository.findProductByName(product.getName());
            if (productOptional.isPresent()) {
                throw new IllegalStateException("Product with this NAME: \"" + product.getName() + "\" already exists.");
            } else {
                oldProduct.setName(product.getName());
            }
        }

        if (product.getMin() != null && !Objects.equals(oldProduct.getMin(), product.getMin())) {
            oldProduct.setMin(product.getMin());
        }

        if (product.getQuantity() != null && !Objects.equals(oldProduct.getQuantity(), product.getQuantity())) {
            oldProduct.setQuantity(product.getQuantity());
        }

        if (product.getProductUnit() != null && !Objects.equals(oldProduct.getProductUnit(), product.getProductUnit())) {
            oldProduct.setProductUnit(product.getProductUnit());
        }
        return oldProduct;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

}
