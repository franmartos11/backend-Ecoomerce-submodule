package com.grupo5.vinylSound.catalog.model.dto.product;

public record ProductResponseDTO(Long id, String title, Float price, String description, String image,
                                 Boolean fav, String subcategory,String category,String brand) {
}
