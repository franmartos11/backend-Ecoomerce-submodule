package com.grupo5.vinylSound.order.repository;

import com.grupo5.vinylSound.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.idUser = :idUser")
    Page<Order> findByIdUser(Pageable pageable, @Param("idUser") String idUser);
}
