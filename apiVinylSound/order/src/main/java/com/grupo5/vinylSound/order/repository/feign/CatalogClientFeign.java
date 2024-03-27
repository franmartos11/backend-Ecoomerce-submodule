package com.grupo5.vinylSound.order.repository.feign;

import com.grupo5.vinylSound.order.model.dto.product.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "catalog",url = "http://localhost:8083")
public interface CatalogClientFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/api/catalog/product/{id}")
    ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id);

}
