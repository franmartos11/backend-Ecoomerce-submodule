package com.grupo5.vinylSound.catalog.model.dto.product;

import com.grupo5.vinylSound.catalog.model.dto.image.ImageDTO;

import java.util.Set;

public record ProductResponseDTO(Long id, String title, Float price, String description,Integer stock,
                                 Set<ImageDTO> images, String subcategory, String category, String brand) {
}
