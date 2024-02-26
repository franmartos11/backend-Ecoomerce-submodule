package com.example.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String title;
    private Float price;
    private String description;
    private String image;
    private Boolean fav;
    private Long idSubcategory;
    private Long idBrand;
}
