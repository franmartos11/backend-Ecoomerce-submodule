package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.dto.payment.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "payment",url = "http://localhost:8084")
public interface PaymentClientFeign {
    @RequestMapping(method = RequestMethod.POST, value = "/user/payment/pay")
    ResponseEntity<?> pay(@RequestBody PaymentDTO request);
}
