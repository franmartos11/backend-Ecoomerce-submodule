package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.idUser = :idUser")
    Optional<Cart> findByIdUser(@Param("idUser") String idUser);
}

