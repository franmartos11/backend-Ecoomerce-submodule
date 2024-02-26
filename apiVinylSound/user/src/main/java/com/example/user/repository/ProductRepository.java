package com.example.user.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@AllArgsConstructor
public class ProductRepository {
    private FeignCatalogRepository feignBillRepository;

}
