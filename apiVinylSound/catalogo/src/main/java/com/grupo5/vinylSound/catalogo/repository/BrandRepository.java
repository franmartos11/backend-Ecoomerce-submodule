package com.grupo5.vinylSound.catalogo.repository;

import com.grupo5.vinylSound.catalogo.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    @Query("select b from Brand b where b.name = :name")
    Optional<Brand> findByName(@Param("name") String name);
}
