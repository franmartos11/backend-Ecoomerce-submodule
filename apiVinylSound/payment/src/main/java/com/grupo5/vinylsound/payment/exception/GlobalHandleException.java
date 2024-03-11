package com.grupo5.vinylsound.payment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandleException {

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<String> badRequest(BadRequestException error) {
    log.error(error.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
  }

  @ExceptionHandler({InternalServerErrorException.class})
  public ResponseEntity<String> internalServerError(InternalServerErrorException error) {
    log.error(error.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
  }

}