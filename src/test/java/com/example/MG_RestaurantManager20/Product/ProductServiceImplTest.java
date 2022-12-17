package com.example.MG_RestaurantManager20.Product;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
import com.example.MG_RestaurantManager20.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private static final String PRODUCT_DEFAULT_NAME = "Banan";
    private static final Double PRODUCT_DEFAULT_MINIMUM_QUANTITY = 1D;
    private static final Double PRODUCT_DEFAULT_QUANTITY = 10D;
    private static final ProductUnit PRODUCT_DEFAULT_UNIT = ProductUnit.UNITS;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Test
    @Transactional
    void findProducts_ProductExists_ReturnListOfAllProducts() {
        var product = new Product(PRODUCT_DEFAULT_NAME, PRODUCT_DEFAULT_MINIMUM_QUANTITY, PRODUCT_DEFAULT_QUANTITY, PRODUCT_DEFAULT_UNIT);
        var product2 = new Product(PRODUCT_DEFAULT_NAME, PRODUCT_DEFAULT_MINIMUM_QUANTITY, PRODUCT_DEFAULT_QUANTITY, PRODUCT_DEFAULT_UNIT);

        when(productRepository.findAll()).thenReturn(List.of(product, product2));

        List<Product> result = productServiceImpl.getProducts();

        assertThat(result).isNotNull();
        assertThat(result).asList().isNotEmpty();
        assertThat(result).asList().hasSize(2);
        assertThat(result).asList().contains(product, product2);
    }

    @Test
    @Transactional
    void findProductById_ProductExists_ReturnProduct() {
        var product = new Product(PRODUCT_DEFAULT_NAME, PRODUCT_DEFAULT_MINIMUM_QUANTITY, PRODUCT_DEFAULT_QUANTITY, PRODUCT_DEFAULT_UNIT);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Product result = productServiceImpl.getProductById(any());

//        assertFalllse(result).contains(product);
        verify(productRepository).findById(any());
    }

    @Test
    @Transactional
    void saveProduct_correctCredentials_returnProduct() {
        var product = new Product(PRODUCT_DEFAULT_NAME, PRODUCT_DEFAULT_MINIMUM_QUANTITY, PRODUCT_DEFAULT_QUANTITY, PRODUCT_DEFAULT_UNIT);

        when(productRepository.save(any())).thenReturn(product);

        Product result = productServiceImpl.addNewProduct(product);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
    }
}