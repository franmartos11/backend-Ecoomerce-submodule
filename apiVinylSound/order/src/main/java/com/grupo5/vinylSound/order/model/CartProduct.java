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
@Table(name = "carts_products")
public class CartProduct {
    @EmbeddedId
    private CartProductId id = new CartProductId();

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false, updatable = false, insertable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer quantity;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class CartProductId implements Serializable {
        private Long cart_id;
        private Long idProduct;
    }

}

