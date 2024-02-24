package com.example.user.controller;

import com.example.user.domain.User;
import com.example.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService service;

    public UserController(UserService service) {this.service = service;}

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        Optional<User> foundUser = service.findById(id);
        if(foundUser.isPresent()){
        return ResponseEntity.ok(foundUser.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
