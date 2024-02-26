package com.grupo5.vinylSound.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150,unique = true)
    private String title;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Boolean fav;

    @ManyToOne
    @JoinColumn(name = "id_subcategory")
    private SubCategory subcategory;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private Brand brand;
}
