package com.example.user.controller;

import com.example.user.exception.NotFoundException;
import com.example.user.model.User;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    public ResponseEntity<User> findById(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

}
