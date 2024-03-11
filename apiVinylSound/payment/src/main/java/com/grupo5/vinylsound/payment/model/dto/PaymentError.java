package com.grupo5.vinylsound.payment.model.dto;

import lombok.Builder;

@Builder
public record PaymentError(
  Integer status,
  String message
) {}