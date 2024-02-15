package com.grupo5.vinylSound.catalogo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150,unique = true)
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<SubCategory> subcategories= new HashSet<>();

    public void addSubCategory(SubCategory subCategory) {
        subcategories.add(subCategory);
        subCategory.setCategory(this);
    }
}
