package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.repository.KeycloakUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private KeycloakUserRepository userRepository;

    public UserService(KeycloakUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String id) {
        var user = userRepository.findById(id);
        return user;
    }

}
