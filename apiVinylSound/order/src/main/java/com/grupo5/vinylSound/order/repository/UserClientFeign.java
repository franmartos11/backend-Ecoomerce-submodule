package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.dto.user.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "user",url = "http://localhost:8082/user")
public interface UserClientFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<UserResponseDTO> findById(@PathVariable String id);
}
