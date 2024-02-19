package com.grupo5.vinylSound.catalogo.controller;

import com.grupo5.vinylSound.catalogo.exception.BadRequestException;
import com.grupo5.vinylSound.catalogo.exception.NotFoundException;
import com.grupo5.vinylSound.catalogo.model.dto.category.CategoryDTO;
import com.grupo5.vinylSound.catalogo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/catalogo/category")
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/create")
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
    public ResponseEntity<String> update(@RequestBody CategoryDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito la categoria correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la categoria", HttpStatus.OK);
    }
}
