package com.grupo5.vinylSound.catalog.service;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Brand;
import com.grupo5.vinylSound.catalog.model.Product;
import com.grupo5.vinylSound.catalog.model.SubCategory;
import com.grupo5.vinylSound.catalog.model.dto.PageRequestDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.ProductDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.ProductResponseDTO;
import com.grupo5.vinylSound.catalog.repository.BrandRepository;
import com.grupo5.vinylSound.catalog.repository.CategoryRepository;
import com.grupo5.vinylSound.catalog.repository.ProductRepository;
import com.grupo5.vinylSound.catalog.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BrandRepository brandRepository;

    public void create(ProductDTO dto) throws BadRequestException {
        if (repository.findByTitle(dto.title()).isPresent()) {
            throw new BadRequestException("Ya existe una producto con el titulo: " + dto.title());
        }

        if (dto.idSubcategory() == null){
            throw new BadRequestException("El id de subcategoria no puede ser nulo");
        }
        if (subCategoryRepository.findById(dto.idSubcategory()).isEmpty()) {
            throw new BadRequestException("No existe una subcategoria con el id: " + dto.idSubcategory());
        }
        if (dto.idBrand() == null){
            throw new BadRequestException("El id de marca no puede ser nulo");
        }
        if (brandRepository.findById(dto.idBrand()).isEmpty()) {
            throw new BadRequestException("No existe una marca con el id: " + dto.idBrand());
        }
        repository.save(mapToProduct(new ProductDTO(null, dto.title(), dto.price(), dto.description(),
                dto.image(),dto.fav(),dto.idSubcategory(),
                dto.idBrand())));
    }

    //Gets //////////////////////////////////////////////////////////////////////////////////////////
    public Page<ProductResponseDTO> getAll(PageRequestDTO dto){
        var products = repository.findAll();
        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTO(product));
        }

        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(dto.getPage());
        holder.setPageSize(dto.getSize());
        var slice = holder.getPageList();

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(dto),listDTO.size());
    }

    public ProductResponseDTO getById(Long id) throws NotFoundException {
        var optionalProduct = repository.findById(id);

        if (optionalProduct.isEmpty()){
            throw new NotFoundException("No hay registro de producto con el id: " + id);
        }

        return mapToDTO(optionalProduct.get());
    }

    public ProductResponseDTO getByTitle(String title) throws NotFoundException {
        var optionalProduct = repository.findByTitle(title);

        if (optionalProduct.isEmpty()){
            throw new NotFoundException("No hay registro de producto con el titulo: " + title);
        }

        return mapToDTO(optionalProduct.get());
    }

    public Page<ProductResponseDTO> getAllFav(PageRequestDTO dto){
        var products = repository.findAllFav();
        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTO(product));
        }

        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(dto.getPage());
        holder.setPageSize(dto.getSize());
        var slice = holder.getPageList();

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(dto),listDTO.size());
    }

    public Page<ProductResponseDTO> filterByCategory(PageRequestDTO pageRequestDTO, Long idCategory) throws NotFoundException {
        var category = categoryRepository.findById(idCategory);
        if (category.isEmpty()){
            throw new NotFoundException("No hay registro de categoria con el id: " + idCategory);
        }
        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findByCategoryId(pageable,idCategory);
        if (products.isEmpty()) {

            return Page.empty();
        }
        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTO(product));
        }
        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(pageRequestDTO.getPage());
        holder.setPageSize(pageRequestDTO.getSize());
        var slice = holder.getPageList();
        var ascending = pageRequestDTO.getSort().isAscending();
        var mutable = new MutableSortDefinition(pageRequestDTO.getSortByColumn(),true,ascending);
        PropertyComparator.sort(slice,mutable);

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(pageRequestDTO),products.getTotalElements());
    }

    public Page<ProductResponseDTO> filterBySubcategory(PageRequestDTO pageRequestDTO, Long idSubcategory) throws NotFoundException {
        var subcategory = subCategoryRepository.findById(idSubcategory);
        if (subcategory.isEmpty()){
            throw new NotFoundException("No hay registro de subcategoria con el id: " + idSubcategory);
        }

        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findBySubcategoryId(pageable,idSubcategory);
        if (products.isEmpty()) {
            return Page.empty();
        }
        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTO(product));
        }
        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(pageRequestDTO.getPage());
        holder.setPageSize(pageRequestDTO.getSize());
        var slice = holder.getPageList();
        var ascending = pageRequestDTO.getSort().isAscending();
        var mutable = new MutableSortDefinition(pageRequestDTO.getSortByColumn(),true,ascending);
        PropertyComparator.sort(slice,mutable);

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(pageRequestDTO),products.getTotalElements());
    }

    public Page<ProductResponseDTO> filterByBrand(PageRequestDTO pageRequestDTO, Long idBrand) throws NotFoundException {
        var brand = brandRepository.findById(idBrand);
        if (brand.isEmpty()){
            throw new NotFoundException("No hay registro de marca con el id: " + idBrand);
        }
        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findByBrandId(pageable,idBrand);
        if (products.isEmpty()) {

            return Page.empty();
        }
        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTO(product));
        }
        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(pageRequestDTO.getPage());
        holder.setPageSize(pageRequestDTO.getSize());
        var slice = holder.getPageList();
        var ascending = pageRequestDTO.getSort().isAscending();
        var mutable = new MutableSortDefinition(pageRequestDTO.getSortByColumn(),true,ascending);
        PropertyComparator.sort(slice,mutable);

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(pageRequestDTO),products.getTotalElements());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void update(ProductDTO dto) throws NotFoundException, BadRequestException {
        var product = repository.findById(dto.id());
        if (product.isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Producto con el id: " + dto.id());
        }

        if (!dto.title().equals(product.get().getTitle())){
            if (repository.findByTitle(dto.title()).isPresent()){
                throw new BadRequestException("Ya hay un registro en la tabla Producto con el titulo: " + dto.title());
            }
        }

        if (subCategoryRepository.findById(dto.idSubcategory()).isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Subcategoria con el id: " + dto.idSubcategory());
        }

        if (brandRepository.findById(dto.idBrand()).isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Marcas con el id: " + dto.idBrand());
        }

        repository.save(mapToProduct(dto));
    }

    public void deleteById(Long id) throws NotFoundException {
        var product = repository.findById(id);
        if(product.isEmpty())
            throw new NotFoundException("El producto no fue encontrado en la base de datos");

        repository.deleteById(id);
    }


    // Maps //////////////////////////////////////////////////////////////////////////////////////////
    private Product mapToProduct(ProductDTO dto) {
        var product = new Product();
        product.setId(dto.id());
        product.setTitle(dto.title());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setFav(dto.fav());

        var subcategory = new SubCategory();
        subcategory.setId(dto.idSubcategory());
        product.setSubcategory(subcategory);

        var brand = new Brand();
        brand.setId(dto.idBrand());
        product.setBrand(brand);

        return product;
    }

    private ProductResponseDTO mapToDTO(Product product){
        return new ProductResponseDTO(
                product.getId(), product.getTitle(), product.getPrice(),
                product.getDescription(), product.getImage(),
                product.getFav(), product.getSubcategory().getId(),
                product.getSubcategory().getCategory().getId(),product.getBrand().getId()
        );
    }
}