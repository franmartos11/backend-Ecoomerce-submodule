package com.grupo5.vinylSound.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idUser;

    @Column(nullable = false)
    private Float total;

    @Column(nullable = false)
    private StatusPayment statusPayment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public void addOrderProducts(OrderProduct orderProduct){
        this.orderProducts.add(orderProduct);
    }

    public void sumTotal(Float total) {
        this.total += total;
    }
}
