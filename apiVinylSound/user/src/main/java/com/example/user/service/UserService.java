package com.example.user.service;

import com.example.user.exception.NotFoundException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(String id) throws NotFoundException {
        var user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new NotFoundException("No hay registro de usuario con el id: " + id);
        }
        return user.get();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void create(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }


}
