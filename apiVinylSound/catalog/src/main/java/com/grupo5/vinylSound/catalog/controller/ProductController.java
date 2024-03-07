package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.dto.PageRequestDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.ProductDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.ProductResponseDTO;
import com.grupo5.vinylSound.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog/product")
public class ProductController {
    private final ProductService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> create(@RequestBody ProductDTO dto)
            throws BadRequestException {
        service.create(dto);
        return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponseDTO>> getAll(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size,
                                                           @RequestParam("sort") Sort.Direction sort,
                                                           @RequestParam("column") String column) {
        return ResponseEntity.ok(service.getAll(new PageRequestDTO(page,size,sort,column)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<ProductResponseDTO> getByTitle(@RequestParam("title") String title) throws NotFoundException {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @GetMapping("/allfav")
    public ResponseEntity<Page<ProductResponseDTO>> getAllFav(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size,
                                                           @RequestParam("sort") Sort.Direction sort,
                                                           @RequestParam("column") String column) {
        return ResponseEntity.ok(service.getAllFav(new PageRequestDTO(page,size,sort,column)));
    }

    @GetMapping("/category={categoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterByCategory(@RequestParam("page") Integer page,
                                                                               @RequestParam("size") Integer size,
                                                                               @RequestParam("sort") Sort.Direction sort,
                                                                               @RequestParam("column") String column,
                                                                               @PathVariable Long categoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterByCategory(new PageRequestDTO(page,size,sort,column),categoryId));
    }

    @GetMapping("/subcategory={subCategoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterBySubcategory(@RequestParam("page") Integer page,
                                                                                  @RequestParam("size") Integer size,
                                                                                  @RequestParam("sort") Sort.Direction sort,
                                                                                  @RequestParam("column") String column,
                                                                                  @PathVariable Long subCategoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterBySubcategory(new PageRequestDTO(page,size,sort,column),subCategoryId));
    }

    @GetMapping("/brand={brandId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterByBrand(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") Sort.Direction sort,
                                                                        @RequestParam("column") String column,
                                                                        @PathVariable Long brandId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterByBrand(new PageRequestDTO(page,size,sort,column),brandId));
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> update(@RequestBody ProductDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito el producto correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<String> deleteById(@PathVariable Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino el producto", HttpStatus.OK);
    }
}
