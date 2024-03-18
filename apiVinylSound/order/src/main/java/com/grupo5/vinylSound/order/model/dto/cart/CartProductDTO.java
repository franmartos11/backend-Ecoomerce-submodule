package com.grupo5.vinylSound.order.model.dto.cart;



import com.grupo5.vinylSound.order.model.dto.product.ImageDTO;

import java.util.Set;

public record CartProductDTO(Long idProduct, Integer quantity, String title, Float price, String description,
                             Set<ImageDTO> images, String subcategory, String category, String brand){
}
