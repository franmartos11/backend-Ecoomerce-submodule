package com.grupo5.vinylsound.payment.model.dto;

import lombok.Builder;

@Builder
public record PaymentResponse(
  String preferenceId
){}