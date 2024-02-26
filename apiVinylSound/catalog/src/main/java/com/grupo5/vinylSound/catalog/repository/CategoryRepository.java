package com.grupo5.vinylSound.catalog.repository;

import com.grupo5.vinylSound.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.name = :name")
    Optional<Category> findByName(@Param("name") String name);
}
