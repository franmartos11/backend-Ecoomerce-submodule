package com.example.user.service;

import com.example.user.exception.NotFoundException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
