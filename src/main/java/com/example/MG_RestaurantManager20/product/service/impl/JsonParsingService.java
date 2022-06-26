package com.example.MG_RestaurantManager20.product.service.impl;

import com.example.MG_RestaurantManager20.product.domain.ProductTypeResponseData;
import com.example.MG_RestaurantManager20.product.domain.ProductInformationResponseData;
import com.example.MG_RestaurantManager20.product.domain.ProductResponseData;
import com.example.MG_RestaurantManager20.product.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JsonParsingService implements ParsingService {

    private final static String HEADER_TRANSLATOR_NAME = "X-RapidAPI-Key";
    @Value("${rapidapi-apikey}")
    private static String HEADER_TRANSLATOR_VALUE;
    private static final String TRANSLATOR_URL = "https://translated-mymemory---translation-memory.p.rapidapi.com/api/get?langpair=pl|en&q=";

    private final static String HEADER_SPOONACULAR_NAME = "x-api-key";
    @Value("${spoonacular-apikey}")
    private static String HEADER_SPOONACULAR_VALUE;
    private final static String SPOONACULAR_ID_URL = "https://api.spoonacular.com/food/products/search?query=";
    private final static String SPOONACULAR_KCAL_URL = "https://api.spoonacular.com/food/products/";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductResponseData parseTranslator(String productPlName) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_TRANSLATOR_NAME, HEADER_TRANSLATOR_VALUE);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = TRANSLATOR_URL + productPlName;

        ResponseEntity<ProductResponseData> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, ProductResponseData.class);

        return response.getBody();
    }

    @Override
    public ProductTypeResponseData parseProductIdByName(String productEngName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_SPOONACULAR_NAME, HEADER_SPOONACULAR_VALUE);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = SPOONACULAR_ID_URL + productEngName + "&number=1";

        ResponseEntity<ProductTypeResponseData> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, ProductTypeResponseData.class);

        return response.getBody();
    }

    @Override
    public ProductInformationResponseData parseProductKcalById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_SPOONACULAR_NAME, HEADER_SPOONACULAR_VALUE);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = SPOONACULAR_KCAL_URL + id.toString();

        ResponseEntity<ProductInformationResponseData> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, ProductInformationResponseData.class);

        return response.getBody();
    }

}
