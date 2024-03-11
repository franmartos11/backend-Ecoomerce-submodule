package com.grupo5.vinylsound.payment.controller;

import com.grupo5.vinylsound.payment.model.dto.PaymentRequest;
import com.grupo5.vinylsound.payment.model.dto.PaymentResponse;
import com.grupo5.vinylsound.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/pay")
  @PreAuthorize("hasRole('ROLE_user')")
  public ResponseEntity<PaymentResponse> pay(@RequestBody PaymentRequest request) {
    var response = paymentService.pay(request);
    return new ResponseEntity<>(PaymentResponse.builder().build(), HttpStatus.OK);
  }

}