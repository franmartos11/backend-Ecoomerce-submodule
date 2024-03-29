package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.dto.brand.BrandDTO;
import com.grupo5.vinylSound.catalog.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BrandController {
    private final BrandService service;

    @PostMapping("/admin/catalog/brand/create")
    public ResponseEntity<String> create(@RequestBody BrandDTO dto)
            throws BadRequestException {
        service.create(dto);
        return new ResponseEntity<>("Marca creada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/api/catalog/brand/all")
    public ResponseEntity<List<BrandDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/api/catalog/brand/{id}")
    public ResponseEntity<BrandDTO> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/admin/catalog/brand/edit")
    public ResponseEntity<String> update(@RequestBody BrandDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito la marca correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/admin/catalog/brand/delete")
    public ResponseEntity<String> deleteById(@RequestParam Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino la marca", HttpStatus.OK);
    }
}
