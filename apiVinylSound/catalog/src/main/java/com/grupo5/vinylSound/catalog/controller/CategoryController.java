package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.dto.category.CategoryDTO;
import com.grupo5.vinylSound.catalog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/catalog/category")
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> create(@RequestBody CategoryDTO dto)
            throws BadRequestException {
        service.create(dto);
        return new ResponseEntity<>("Categoria creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> update(@RequestBody CategoryDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito la categoria correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la categoria", HttpStatus.OK);
    }
}
