package com.grupo5.vinylSound.catalog.model.dto.product;

public record ProductDTO(Long id, String title, Float price, String description, String image,
                         Boolean fav, Long idSubcategory, Long idBrand) {
}