package com.grupo5.vinylSound.order.model.dto.order;

import java.util.List;

public record OrderRequestDTO(String idUser, List<ProductOrderRequestDTO> products) {
}

