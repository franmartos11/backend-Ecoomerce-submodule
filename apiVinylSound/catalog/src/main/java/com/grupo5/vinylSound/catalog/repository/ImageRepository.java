package com.grupo5.vinylSound.catalog.repository;

import com.grupo5.vinylSound.catalog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("SELECT i FROM Image i WHERE i.product.id = :productId")
    List<Image> findByProductId(@Param("productId") Long productId);


    @Query("SELECT i FROM Image i WHERE i.product.title = :productTitle")
    List<Image> findByProductTitle(@Param("productTitle") String productTitle);
}
