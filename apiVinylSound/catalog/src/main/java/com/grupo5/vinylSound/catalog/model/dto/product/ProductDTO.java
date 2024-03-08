package com.grupo5.vinylSound.catalog.model.dto.product;

import com.grupo5.vinylSound.catalog.model.dto.image.ImageProductDTO;

import java.util.List;

public record ProductDTO(Long id, String title, Float price, String description,
                         Long idSubcategory, Long idBrand, List<ImageProductDTO> images) {
}
