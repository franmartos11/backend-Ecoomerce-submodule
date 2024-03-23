package com.grupo5.vinylSound.catalog.controller;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.dto.PageRequestDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.*;
import com.grupo5.vinylSound.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService service;

    @PostMapping("/admin/catalog/product/create")
    public ResponseEntity<String> create(@RequestBody ProductDTO dto)
            throws BadRequestException {
        service.create(dto);
        return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/api/catalog/product/all")
    public ResponseEntity<Page<ProductResponseDTO>> getAll(@RequestParam("page") Integer page,
                                                           @RequestParam("size") Integer size,
                                                           @RequestParam("sort") Sort.Direction sort,
                                                           @RequestParam("column") String column) {
        return ResponseEntity.ok(service.getAll(new PageRequestDTO(page,size,sort,column)));
    }

    @GetMapping("/api/catalog/product/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/api/catalog/product/search")
    public ResponseEntity<ProductResponseDTO> getByTitle(@RequestParam("title") String title) throws NotFoundException {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @GetMapping("/api/catalog/product/category={categoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterByCategory(@RequestParam("page") Integer page,
                                                                               @RequestParam("size") Integer size,
                                                                               @RequestParam("sort") Sort.Direction sort,
                                                                               @RequestParam("column") String column,
                                                                               @PathVariable Long categoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterByCategory(new PageRequestDTO(page,size,sort,column),categoryId));
    }

    @GetMapping("/api/catalog/product/subcategory={subCategoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterBySubcategory(@RequestParam("page") Integer page,
                                                                                  @RequestParam("size") Integer size,
                                                                                  @RequestParam("sort") Sort.Direction sort,
                                                                                  @RequestParam("column") String column,
                                                                                  @PathVariable Long subCategoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterBySubcategory(new PageRequestDTO(page,size,sort,column),subCategoryId));
    }

    @GetMapping("/api/catalog/product/brand={brandId}")
    public ResponseEntity<Page<ProductResponseDTO>> filterByBrand(@RequestParam("page") Integer page,
                                                                        @RequestParam("size") Integer size,
                                                                        @RequestParam("sort") Sort.Direction sort,
                                                                        @RequestParam("column") String column,
                                                                        @PathVariable Long brandId)
            throws NotFoundException {
        return ResponseEntity.ok(service.filterByBrand(new PageRequestDTO(page,size,sort,column),brandId));
    }

    @GetMapping("/admin/catalog/product/topSells/all")
    public ResponseEntity<Page<ProductSalesDTO>> findTopSellingProductsAdmin(@RequestParam(value = "page", defaultValue = "0")
                                                                                Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "10")
                                                                            Integer size) {
        return ResponseEntity.ok(service.findTopSellingProductsAdmin(page,size));
    }

    @GetMapping("/api/catalog/product/topSells/all")
    public ResponseEntity<Page<ProductSalesImageDTO>> findTopSellingProducts(@RequestParam(value = "page", defaultValue = "0")
                                                                        Integer page,
                                                                             @RequestParam(value = "size", defaultValue = "10")
                                                                        Integer size) {
        return ResponseEntity.ok(service.findTopSellingProducts(page,size));
    }

    @GetMapping("/admin/catalog/product/topSells/category={categoryId}")
    public ResponseEntity<Page<ProductSalesDTO>> findTopSellingProductsByCategory(@RequestParam(value = "page", defaultValue = "0")
                                                                                          Integer page,
                                                                                      @RequestParam(value = "size", defaultValue = "10")
                                                                                          Integer size,
                                                                                     @PathVariable Long categoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.findTopSellingProductsByCategory(categoryId,page,size));
    }

    @GetMapping("/admin/catalog/product/topSells/subcategory={subcategoryId}")
    public ResponseEntity<Page<ProductSalesDTO>> findTopSellingProductsBySubcategory(@RequestParam(value = "page", defaultValue = "0")
                                                                                            Integer page,
                                                                                        @RequestParam(value = "size", defaultValue = "10")
                                                                                            Integer size,
                                                                                     @PathVariable Long subcategoryId)
            throws NotFoundException {
        return ResponseEntity.ok(service.findTopSellingProductsBySubcategory(subcategoryId,page,size));
    }

    @GetMapping("/admin/catalog/product/topSells/brand={brandId}")
    public ResponseEntity<Page<ProductSalesDTO>> findTopSellingProductsByBrand(@RequestParam(value = "page", defaultValue = "0")
                                                                                      Integer page,
                                                                                  @RequestParam(value = "size", defaultValue = "10")
                                                                                      Integer size,
                                                                                        @PathVariable Long brandId)
            throws NotFoundException {
        return ResponseEntity.ok(service.findTopSellingProductsByBrand(brandId,page,size));
    }

    @GetMapping("/admin/catalog/product/zeroSales")
    public ResponseEntity<Page<ProductSalesDTO>> findProductsWithZeroSells(@RequestParam(value = "page", defaultValue = "0")
                                                                                  Integer page,
                                                                              @RequestParam(value = "size", defaultValue = "10")
                                                                                  Integer size) {
        return ResponseEntity.ok(service.findProductsWithZeroSells(page, size));
    }

    @GetMapping("/admin/catalog/product/SalesLessThan={number}")
    public ResponseEntity<Page<ProductSalesDTO>> findSalesProductsLessThan(@RequestParam(value = "page", defaultValue = "0")
                                                                        Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "10")
                                                                        Integer size,
                                                                        @PathVariable Integer number) {
        return ResponseEntity.ok(service.findSalesProductsLessThan(page,size,number));
    }

    @PutMapping("/admin/catalog/product/edit")
    public ResponseEntity<String> update(@RequestBody ProductDTO dto) throws NotFoundException, BadRequestException {
        service.update(dto);
        return new ResponseEntity<>("Se edito el producto correctamente",HttpStatus.OK);
    }


    @PutMapping("/user/catalog/product/decreaseStock")
    public ResponseEntity<String> decreaseStock(@RequestBody List<ProductStockRequestDTO> products)
            throws NotFoundException, BadRequestException {
        service.decreaseStock(products);
        return new ResponseEntity<>("Se realizó correctamente la modificacion de stock",HttpStatus.OK);
    }

    @DeleteMapping("/admin/catalog/product/delete")
    public ResponseEntity<String> deleteById(@RequestParam Long id) throws NotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>("Se elimino el producto", HttpStatus.OK);
    }
}
