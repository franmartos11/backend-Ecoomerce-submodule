package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {

    @Query("SELECT cp FROM CartProduct cp WHERE cp.cart.id = :idCart")
    List<CartProduct> findByCartId(Long idCart);

    @Transactional
    void deleteById(CartProduct.CartProductId id);

    @Transactional
    Optional<CartProduct> findById(CartProduct.CartProductId id);
}
