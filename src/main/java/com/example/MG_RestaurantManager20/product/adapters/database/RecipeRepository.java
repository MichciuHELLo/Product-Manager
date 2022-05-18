package com.example.MG_RestaurantManager20.product.adapters.database;


import com.example.MG_RestaurantManager20.product.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT s FROM Recipe s WHERE s.name = ?1")
    Optional<Recipe> findProductByName(String convertedName);
}

