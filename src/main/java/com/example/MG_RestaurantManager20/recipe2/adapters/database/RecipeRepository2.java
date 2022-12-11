package com.example.MG_RestaurantManager20.recipe2.adapters.database;

import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository2 extends JpaRepository<Recipe2, Long> {

//    @Query("SELECT s FROM Recipe2 s WHERE s.name = ?1")
//    Recipe2 getRecipeByName(String convertedName);

    @Modifying
//    @Query("update Recipe2 r set r.name = ?1, r.description = ?2, r.requiredProducts = ?3 where r.id = ?4")
    @Query("update Recipe2 r set r.name = ?1, r.description = ?2 where r.id = ?3")
    void updateEmployeeById(String name, String description, Long recipeId);

}
