package com.example.MG_RestaurantManager20.product.service.port;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
import com.example.MG_RestaurantManager20.product.service.ports.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;


    @Test
    void getProduct_ByID() {


        var product = new Product("PiccaTestowa", 1.0, 2.0, ProductUnit.UNITS);
        var id = product.getId();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProduct(id);
        assertThat(result).contains(product);
        verify(productRepository).findById(id);

    }
}
