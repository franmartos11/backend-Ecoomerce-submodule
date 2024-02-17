package com.grupo5.vinylSound.catalogo.repository;

import com.grupo5.vinylSound.catalogo.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("SELECT s FROM SubCategory s WHERE s.name = :name")
    Optional<SubCategory> findByName(@Param("name") String name);

}
