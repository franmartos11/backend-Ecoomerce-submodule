package com.grupo5.vinylSound.catalogo.repository;

import com.grupo5.vinylSound.catalogo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.title = :title")
    Optional<Product> findByTitle(@Param("title") String title);
    @Query("SELECT p FROM Product p JOIN p.subcategory s JOIN s.category c WHERE c.id = :categoryId")
    Page<Product> findByCategoryId(Pageable pageable, @Param("categoryId") Long categoryId);
    @Query("SELECT p FROM Product p JOIN p.subcategory s WHERE s.id = :subcategoryId")
    Page<Product> findBySubcategoryId(Pageable pageable, @Param("subcategoryId") Long subcategoryId);
    @Query("SELECT p FROM Product p JOIN p.brand b WHERE b.id = :brandId")
    Page<Product> findByBrandId(Pageable pageable, @Param("brandId") Long brandId);
    @Query("SELECT p FROM Product p WHERE p.fav = true")
    List<Product> findAllFav();
}
