package com.grupo5.vinylSound.order.model.dto.order;

import com.grupo5.vinylSound.order.model.StatusOrder;

import java.util.List;

public record OrderResponseDTO(Long id, String idUser, List<ProductOrderResponseDTO> products,
                               Float total, StatusOrder status) {
}
