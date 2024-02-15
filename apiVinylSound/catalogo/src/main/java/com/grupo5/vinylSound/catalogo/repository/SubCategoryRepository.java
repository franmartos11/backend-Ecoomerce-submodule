package com.grupo5.vinylSound.catalogo.repository;

import com.grupo5.vinylSound.catalogo.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
}
