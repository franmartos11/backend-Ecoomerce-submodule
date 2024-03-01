package com.grupo5.vinylSound.user.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record ProductDTO(Long id,String title,Float price,String description,String image,
                        Boolean fav,Long idSubcategory,Long idBrand) {

}
