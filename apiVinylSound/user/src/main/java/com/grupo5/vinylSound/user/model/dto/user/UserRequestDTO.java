package com.grupo5.vinylSound.user.model.dto.user;

public record UserRequestDTO(String name,String lastName,String email,
                      String password,String city,String postalCode, String address) {
}
