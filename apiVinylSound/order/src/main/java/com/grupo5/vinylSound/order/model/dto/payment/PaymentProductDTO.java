package com.grupo5.vinylSound.order.model.dto.payment;

public record PaymentProductDTO (String id, String title, String description,
                                 String image_url, String category, Integer quantity, Double price){}