package com.example.MG_RestaurantManager20.recipe2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class RequiredProducts {

    @Id
    private Long requiredProductId;

    // TODO zamiast name -> product_fk
//    private Long product_fk;
    private String name;

    private int quantity;

    private Long recipe_fk;

}
