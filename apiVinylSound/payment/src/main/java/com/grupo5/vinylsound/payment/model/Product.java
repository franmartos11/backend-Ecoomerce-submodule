package com.grupo5.vinylsound.payment.model;

public record Product (
  String id,
  String title,
  String description,
  String image_url,
  String category,
  Integer quantity,
  Double price
){}