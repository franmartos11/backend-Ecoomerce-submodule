package com.grupo5.vinylSound.order.model.dto.cart;

import java.util.List;

public record CartResponseDTO(Long id, String idUser, List<CartProductDTO> products) {
}
