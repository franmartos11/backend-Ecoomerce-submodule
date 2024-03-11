package com.grupo5.vinylsound.payment.exception;

public class BadRequestException extends Exception{
  public BadRequestException(String message) {
    super(message);
  }
}