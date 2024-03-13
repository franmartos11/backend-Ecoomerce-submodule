package com.grupo5.vinylSound.order.exception;

public class BadRequestException extends Exception{
  public BadRequestException(String message) {
    super(message);
  }
}