package com.example.user.repository;

import com.example.user.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
public class ProductRepository {
    private FeignCatalogRepository feignCatalogRepository;

    public List<ProductDTO> findAll(){
        ResponseEntity<List<ProductDTO>> response = feignCatalogRepository.findAll();
        return response.getBody();
    }

}
