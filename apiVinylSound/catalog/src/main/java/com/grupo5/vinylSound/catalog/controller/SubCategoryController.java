package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.dto.subcategory.SubCategoryDTO;
import com.grupo5.vinylSound.catalog.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/subcategory")
public class SubCategoryController {
    private final SubCategoryService service;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody SubCategoryDTO dto)
            throws BadRequestException {
        service.create(dto);
        return new ResponseEntity<>("Subcategoria creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDTO> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody SubCategoryDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito la subcategoria correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la subcategoria", HttpStatus.OK);
    }
}
