package com.example.MG_RestaurantManager20.recipe.adapters.database;

import com.example.MG_RestaurantManager20.recipe.domain.RequiredProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequiredProductsRepository extends JpaRepository<RequiredProducts, Long> {

    @Query("SELECT e FROM RequiredProducts e WHERE e.recipe_fk = ?1")
    List<RequiredProducts> getAllRequiredProductsByRecipeId(Long recipeId);

}
