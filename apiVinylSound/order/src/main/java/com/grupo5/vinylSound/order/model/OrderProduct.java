package com.grupo5.vinylSound.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders_products")
public class OrderProduct {
    @EmbeddedId
    private OrderProduct.OrderProductId id = new OrderProduct.OrderProductId();

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false, insertable = false)
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float subtotal;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class OrderProductId implements Serializable {
        private Long order_id;
        private Long idProduct;
    }
}
