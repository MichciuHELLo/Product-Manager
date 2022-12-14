package com.example.MG_RestaurantManager20.recipe2.service.impl;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
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
    final private ProductService productService;

    @Override
    public List<RequiredProducts> getAllRequiredProducts() {
        return requiredProductsRepository.findAll();
    }

    @Override
    public List<RequiredProducts> getAllRequiredProductsByRecipeId(Long recipeId) {
        List<RequiredProducts> requiredProducts = requiredProductsRepository.getAllRequiredProductsByRecipeId(recipeId);

        for (RequiredProducts requiredProduct : requiredProducts) {
            Product product = productService.getProductById(requiredProduct.getProduct_fk());
            requiredProduct.setName(product.getName());
        }

        return requiredProducts;
    }

    @Override
    public RequiredProducts addRequiredProductToRecipe(Long recipeId, Long productId, Double quantity) {
        return requiredProductsRepository.save(new RequiredProducts(recipeId, productId, quantity));
    }

    @Override
    public void deleteSelectedRequiredProducts(Set<RequiredProducts> requiredProducts) {
        var idList = requiredProducts.stream()
                .map(RequiredProducts::getRequiredProductId).collect(Collectors.toList());

        requiredProductsRepository.deleteAllById(idList);
    }
}
