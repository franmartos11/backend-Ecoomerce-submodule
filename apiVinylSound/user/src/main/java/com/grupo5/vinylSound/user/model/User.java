package com.grupo5.vinylSound.user.model;

import com.grupo5.vinylSound.user.model.dto.product.ProductDTO;
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
    private String username;
    private String email;
    private Boolean isEmailVerified;
    private String password;
    private String city;
    private String postalCode;
    private String address;
    private List<ProductDTO> productsFav;
}
