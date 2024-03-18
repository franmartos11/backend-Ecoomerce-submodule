package com.grupo5.vinylSound.order.model.dto.order;

import com.grupo5.vinylSound.order.model.StatusPayment;

import java.util.List;

public record OrderResponseDTO(Long id, String idUser, List<ProductOrderResponseDTO> products,
                               Float total, StatusPayment status) {
}
