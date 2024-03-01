package com.grupo5.vinylSound.user.controller;

import com.grupo5.vinylSound.user.exception.BadRequestException;
import com.grupo5.vinylSound.user.exception.InternalServerErrorException;
import com.grupo5.vinylSound.user.exception.NotFoundException;
import com.grupo5.vinylSound.user.model.dto.user.UserEditDTO;
import com.grupo5.vinylSound.user.model.dto.user.UserRequestDTO;
import com.grupo5.vinylSound.user.model.dto.user.UserResponseDTO;
import com.grupo5.vinylSound.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserRequestDTO dto) throws BadRequestException, InternalServerErrorException {
        service.create(dto);
        return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> update(@RequestBody UserEditDTO dto) throws NotFoundException, BadRequestException {
        service.edit(dto);
        return new ResponseEntity<>("Se edito el usuario correctamente",HttpStatus.OK);
    }

    @PutMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) throws NotFoundException {
        service.forgotPassword(email);
        return new ResponseEntity<>("Se envio un mail para recuperar la contraseña",HttpStatus.OK);
    }

    @PutMapping("/reset-password/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_user')")
    public ResponseEntity<String> resetPassword(@PathVariable String id,@RequestParam String password) throws NotFoundException {
        service.resetPassword(password,id);
        return new ResponseEntity<>("Se modifico la contraseña correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> deleteById(@PathVariable String id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino el usuario", HttpStatus.OK);
    }

}
