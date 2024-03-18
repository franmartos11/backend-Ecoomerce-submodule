package com.grupo5.vinylSound.catalog.model.dto.product;

public record ProductSalesDTO(Long id, String title, Float price, Integer quantitySells,
                              String subcategory, String category, String brand) {
}
