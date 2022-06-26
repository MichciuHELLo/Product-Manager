package com.example.MG_RestaurantManager20.recipe.adapters.database;


import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT s FROM Recipe s WHERE s.name = ?1")
    Optional<Recipe> findProductByName(String convertedName);

    @Modifying
    @Query("UPDATE Recipe s SET s.calories=:calories WHERE s.id=:id")
    void updateRecipeCalories(Long id, Double calories);

    @Query("SELECT s.calories FROM Recipe s WHERE s.id=:id")
    Double getRecipeCaloriesById(Long id);

}

