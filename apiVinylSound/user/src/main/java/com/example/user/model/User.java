package com.example.user.model;

import com.example.user.model.dto.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String city;
    private String postalCode;
    private String address;
    private List<ProductDTO> productsFav;
}
