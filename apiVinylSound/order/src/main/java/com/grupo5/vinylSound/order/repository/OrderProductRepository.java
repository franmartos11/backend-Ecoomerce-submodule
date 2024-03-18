package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

    @Query("SELECT op FROM OrderProduct op WHERE op.order.id = :idOrder")
    List<OrderProduct> findAllByOrderId(Long idOrder);


}

