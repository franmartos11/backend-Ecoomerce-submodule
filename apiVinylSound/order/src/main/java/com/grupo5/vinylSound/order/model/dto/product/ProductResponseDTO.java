package com.grupo5.vinylSound.order.model.dto.product;

import java.util.Set;

public record ProductResponseDTO(Long id, String title, Float price, String description, Set<ImageDTO> images,
                                 String subcategory, String category, String brand) {
}
