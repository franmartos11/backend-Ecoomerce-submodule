package com.grupo5.vinylsound.payment.service;

import com.grupo5.vinylsound.payment.model.Payment;
import com.grupo5.vinylsound.payment.model.dto.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  public Payment pay(PaymentRequest request) {
    return Payment.builder().build();
  }

}