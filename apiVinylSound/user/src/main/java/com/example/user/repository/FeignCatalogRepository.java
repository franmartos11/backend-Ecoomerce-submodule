package com.example.user.repository;

import com.example.user.config.feign.OAuthFeignConfig;
import com.example.user.model.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name= "catalog-server",url = "http://localhost:8083",configuration = OAuthFeignConfig.class)
public interface FeignCatalogRepository {
    @RequestMapping(method = RequestMethod.GET,value = "/catalog/product/all")
    ResponseEntity<List<ProductDTO>> findAll();

}
