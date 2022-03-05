package com.example.MG_RestaurantManager20.product.service.ports;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //    @Get
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    //    @Get
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    //    @Post
    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }


    //    @Put
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

    //    @Delete
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    //    @Delete
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

}
