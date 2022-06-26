package com.example.MG_RestaurantManager20.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties
public class ProductInformationResponseData {

    private Long id;
    private String title;
    private ProductNutrientsResponseData nutrition;

}
