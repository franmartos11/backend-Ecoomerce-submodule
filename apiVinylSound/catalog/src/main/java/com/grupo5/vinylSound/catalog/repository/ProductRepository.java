package com.grupo5.vinylSound.catalog.repository;

import com.grupo5.vinylSound.catalog.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query("SELECT p FROM Product p WHERE p.quantitySells > 0 ORDER BY p.quantitySells DESC")
    Page<Product> findTopSellingProducts(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.quantitySells > 0 AND p.subcategory.category.id = :categoryId " +
            "ORDER BY p.quantitySells DESC")
    Page<Product> findTopSellingProductsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.quantitySells > 0 AND p.subcategory.id = :subcategoryId " +
            "ORDER BY p.quantitySells DESC")
    Page<Product> findTopSellingProductsBySubcategory(@Param("subcategoryId") Long subcategoryId, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.quantitySells > 0 AND p.brand.id = :brandId " +
            "ORDER BY p.quantitySells DESC")
    Page<Product> findTopSellingProductsByBrand(@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.quantitySells = 0")
    Page<Product> findProductsWithZeroSales(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.quantitySells < :number " +
            "ORDER BY p.quantitySells DESC")
    Page<Product> findSalesProductsLessThan(@Param("number") Integer number, Pageable pageable);
}
