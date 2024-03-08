package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/image")
public class ImageController {
    private final ImageService service;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la imagen", HttpStatus.OK);
    }
}
