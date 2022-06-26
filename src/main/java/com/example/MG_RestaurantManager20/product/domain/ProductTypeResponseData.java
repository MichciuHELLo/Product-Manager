package com.example.MG_RestaurantManager20.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties
public class ProductTypeResponseData {

    private String type;
    private List<ProductIdResponseData> products;

}
