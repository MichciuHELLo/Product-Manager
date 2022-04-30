package com.example.MG_RestaurantManager20.product.adapters.database;

import com.example.MG_RestaurantManager20.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT s FROM Product s WHERE s.name = ?1")
    Optional<Product> findProductByName(String name);
    
}
