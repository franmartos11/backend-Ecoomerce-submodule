package com.grupo5.vinylSound.catalogo.repository;

import com.grupo5.vinylSound.catalogo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
