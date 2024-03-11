package com.grupo5.vinylsound.payment.model.dto;

import com.grupo5.vinylsound.payment.model.Product;

import java.util.List;

public record PaymentRequest (
  List<Product> products
){}