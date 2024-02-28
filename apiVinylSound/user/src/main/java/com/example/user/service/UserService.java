package com.example.user.service;

import com.example.user.exception.NotFoundException;
import com.example.user.model.User;
import com.example.user.repository.ProductRepository;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public User getById(String id) throws NotFoundException {
        var user = userRepository.findById(id);
        var products = productRepository.findAll();

        if (user.isEmpty()){
            throw new NotFoundException("No hay registro de usuario con el id: " + id);
        }
        user.get().setProductsFav(products);
        return user.get();
    }

}
