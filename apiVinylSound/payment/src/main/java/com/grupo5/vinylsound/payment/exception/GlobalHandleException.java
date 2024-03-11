package com.grupo5.vinylsound.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<String> badRequest(BadRequestException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler({InternalServerErrorException.class})
  public ResponseEntity<String> internalServerError(InternalServerErrorException e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }

}