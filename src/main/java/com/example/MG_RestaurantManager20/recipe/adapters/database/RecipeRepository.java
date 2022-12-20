package com.example.MG_RestaurantManager20.recipe.adapters.database;

import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

//    @Query("SELECT s FROM Recipe2 s WHERE s.name = ?1")
//    Recipe2 getRecipeByName(String convertedName);

    @Query("SELECT r FROM Recipe r WHERE r.user_fk = ?1")
    List<Recipe> getRecipesByUsersSessionId(Long id);

    @Modifying
//    @Query("update Recipe2 r set r.name = ?1, r.description = ?2, r.requiredProducts = ?3 where r.id = ?4")
    @Query("update Recipe r set r.name = ?1, r.description = ?2 where r.id = ?3")
    void updateEmployeeById(String name, String description, Long recipeId);

}
