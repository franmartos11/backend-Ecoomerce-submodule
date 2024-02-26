package com.example.user.repository;

import com.example.user.config.feign.OAuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name= "catalog-server",url = "http://localhost:8181",configuration = OAuthFeignConfig.class)
public interface FeignCatalogRepository {

}
