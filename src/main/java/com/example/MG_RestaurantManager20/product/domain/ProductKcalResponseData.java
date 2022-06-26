package com.example.MG_RestaurantManager20.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties
public class ProductKcalResponseData {

    private String name;
    private Double amount;
    private String unit;
    private Double percentOfDailyNeeds;

}
