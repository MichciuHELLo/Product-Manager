package com.example.MG_RestaurantManager20.product.service;

import com.example.MG_RestaurantManager20.product.domain.ProductTypeResponseData;
import com.example.MG_RestaurantManager20.product.domain.ProductInformationResponseData;
import com.example.MG_RestaurantManager20.product.domain.ProductResponseData;

public interface JsonParsingService {

    ProductResponseData parseTranslator(String productPlName);

    ProductTypeResponseData parseProductIdByName(String productEngName);

    ProductInformationResponseData parseProductKcalById(Long id);

}
