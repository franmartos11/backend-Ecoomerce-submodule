package com.grupo5.vinylsound.payment.controller;

import com.grupo5.vinylsound.payment.exception.InternalServerErrorException;
import com.grupo5.vinylsound.payment.model.dto.PaymentError;
import com.grupo5.vinylsound.payment.model.dto.PaymentRequest;
import com.grupo5.vinylsound.payment.model.dto.PaymentResponse;
import com.grupo5.vinylsound.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/pay")
  public ResponseEntity<?> pay(@RequestBody PaymentRequest request) {
    try {
      var response = PaymentResponse.builder()
        .preferenceId(paymentService.pay(request.products()))
        .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (InternalServerErrorException error) {
      var response = PaymentError.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(error.getMessage())
        .build();
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}