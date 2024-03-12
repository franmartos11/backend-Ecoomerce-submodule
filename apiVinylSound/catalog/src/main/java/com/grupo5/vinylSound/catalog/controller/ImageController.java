package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/image")
public class ImageController {
    private final ImageService service;

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> deleteById(@RequestParam Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la imagen", HttpStatus.OK);
    }
}
