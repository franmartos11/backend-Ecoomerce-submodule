package com.grupo5.vinylSound.catalogo.model.dto.product;

public record ProductDTO(Long id, String title, Float price, String description, String image,
                         Boolean fav, Long idSubcategory, Long idBrand) {
}
