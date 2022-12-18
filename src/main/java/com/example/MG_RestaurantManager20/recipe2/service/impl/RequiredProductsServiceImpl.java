package com.example.MG_RestaurantManager20.recipe2.service.impl;

import com.example.MG_RestaurantManager20.recipe2.adapters.database.RequiredProductsRepository;
import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import com.example.MG_RestaurantManager20.recipe2.service.RequiredProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequiredProductsServiceImpl implements RequiredProductsService {

    final private RequiredProductsRepository requiredProductsRepository;

    @Override
    public List<RequiredProducts> getAllRequiredProducts() {
        return requiredProductsRepository.findAll();
    }

    @Override
    public List<RequiredProducts> getAllRequiredProductsByRecipeId(Long recipeId) {
        return requiredProductsRepository.getAllRequiredProductsByRecipeId(recipeId);
    }

    @Override
    public RequiredProducts addRequiredProductToRecipe(Long recipeId, Long productId, String name, Double quantity) {
        return requiredProductsRepository.save(new RequiredProducts(recipeId, productId, name, quantity));
    }

    @Override
    public void deleteSelectedRequiredProducts(Set<RequiredProducts> requiredProducts) {
        var idList = requiredProducts.stream()
                .map(RequiredProducts::getRequiredProductId).collect(Collectors.toList());

        requiredProductsRepository.deleteAllById(idList);
    }
}
