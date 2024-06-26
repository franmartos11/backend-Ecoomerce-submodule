package com.grupo5.vinylSound.catalog.service;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Brand;
import com.grupo5.vinylSound.catalog.model.Product;
import com.grupo5.vinylSound.catalog.model.SubCategory;
import com.grupo5.vinylSound.catalog.model.dto.PageRequestDTO;
import com.grupo5.vinylSound.catalog.model.dto.image.ImageProductDTO;
import com.grupo5.vinylSound.catalog.model.dto.image.ImageDTO;
import com.grupo5.vinylSound.catalog.model.dto.order.ProductOrderResponseDTO;
import com.grupo5.vinylSound.catalog.model.dto.product.*;
import com.grupo5.vinylSound.catalog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final BrandRepository brandRepository;
    private final ImageService imageService;

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

        var persist = repository.save(mapToProduct(new ProductDTO(null, dto.title(), dto.price(), dto.description(),
                dto.stock(), dto.idSubcategory(), dto.idBrand(),null)));

        for (ImageProductDTO each:dto.images()) {
            imageService.createImage(new ImageDTO(null,each.name(), each.url(), persist.getId()));
        }

    }

    //Gets //////////////////////////////////////////////////////////////////////////////////////////
    public Page<ProductResponseDTO> getAll(PageRequestDTO dto){
        var products = repository.findAll();
        if (products.isEmpty()) {
            return Page.empty();
        }


        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            var images = imageService.getAllByIdProduct(product.getId());
            listDTO.add(mapToDTO(product,images));
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

        var images = imageService.getAllByIdProduct(id);

        return mapToDTO(optionalProduct.get(),images);
    }

    public ProductResponseDTO getByTitle(String title) throws NotFoundException {
        var optionalProduct = repository.findByTitle(title);

        if (optionalProduct.isEmpty()){
            throw new NotFoundException("No hay registro de producto con el titulo: " + title);
        }

        var images = imageService.getAllByTitleProduct(title);

        return mapToDTO(optionalProduct.get(),images);
    }

    public Page<ProductResponseDTO> filterByCategory(PageRequestDTO pageRequestDTO, Long idCategory) throws NotFoundException {

        if (categoryRepository.findById(idCategory).isEmpty()){
            throw new NotFoundException("No hay registro de categoria con el id: " + idCategory);
        }

        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findByCategoryId(pageable,idCategory);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            var images = imageService.getAllByIdProduct(product.getId());
            listDTO.add(mapToDTO(product,images));
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
        if (subCategoryRepository.findById(idSubcategory).isEmpty()){
            throw new NotFoundException("No hay registro de subcategoria con el id: " + idSubcategory);
        }

        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findBySubcategoryId(pageable,idSubcategory);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            var images = imageService.getAllByIdProduct(product.getId());
            listDTO.add(mapToDTO(product,images));
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
        if (brandRepository.findById(idBrand).isEmpty()){
            throw new NotFoundException("No hay registro de marca con el id: " + idBrand);
        }

        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var products = repository.findByBrandId(pageable,idBrand);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductResponseDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            var images = imageService.getAllByIdProduct(product.getId());
            listDTO.add(mapToDTO(product,images));
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

    public Page<ProductSalesDTO> findTopSellingProductsAdmin(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        var products = repository.findTopSellingProducts(pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }
        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }

    public Page<ProductSalesImageDTO> findTopSellingProducts(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        var products = repository.findTopSellingProducts(pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesImageDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            var images = imageService.getAllByIdProduct(product.getId());
            listDTO.add(mapToDTOSalesImage(product,images));
        }
        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }



    public Page<ProductSalesDTO> findTopSellingProductsByCategory(Long categoryId,Integer page, Integer size) throws NotFoundException {
        if (categoryRepository.findById(categoryId).isEmpty()){
            throw new NotFoundException("No hay registro de categoria con el id: " + categoryId);
        }

        var pageable = PageRequest.of(page, size);
        var products = repository.findTopSellingProductsByCategory(categoryId,pageable);


        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }

        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }

    public Page<ProductSalesDTO> findTopSellingProductsBySubcategory(Long subCategoryId,Integer page, Integer size) throws NotFoundException {
        if (subCategoryRepository.findById(subCategoryId).isEmpty()){
            throw new NotFoundException("No hay registro de subcategoria con el id: " + subCategoryId);
        }

        var pageable = PageRequest.of(page, size);
        var products = repository.findTopSellingProductsBySubcategory(subCategoryId,pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }

        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }

    public Page<ProductSalesDTO> findTopSellingProductsByBrand(Long brandId,Integer page, Integer size) throws NotFoundException {
        if (subCategoryRepository.findById(brandId).isEmpty()){
            throw new NotFoundException("No hay registro de marca con el id: " + brandId);
        }

        var pageable = PageRequest.of(page, size);
        var products = repository.findTopSellingProductsByBrand(brandId,pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }

        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }

    public Page<ProductSalesDTO> findProductsWithZeroSells(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        var products = repository.findProductsWithZeroSales(pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }
        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
    }

    public Page<ProductSalesDTO> findSalesProductsLessThan(Integer page, Integer size,Integer number) {
        var pageable = PageRequest.of(page, size);
        var products = repository.findSalesProductsLessThan(number,pageable);

        if (products.isEmpty()) {
            return Page.empty();
        }

        List<ProductSalesDTO> listDTO = new ArrayList<>();
        for (Product product : products) {
            listDTO.add(mapToDTOSales(product));
        }

        return new PageImpl<>(listDTO,pageable,products.getTotalElements());
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
        for (ImageProductDTO image :dto.images()) {
            imageService.update(image, dto.id());
        }

    }

    public void editStockAndSell(List<ProductOrderResponseDTO> products){
        for (ProductOrderResponseDTO dto:products) {
            var optional = repository.findById(dto.idProduct());
            if (optional.isPresent()) {
                var product = optional.get();
                var newQuantity = product.getQuantitySells() + dto.quantity();
                product.setQuantitySells(newQuantity);

                var newStock = product.getStock() - dto.quantity();
                product.setStock(newStock);

                repository.save(product);
            }
        }
    }

    public void decreaseStock(List<ProductStockRequestDTO> products) throws NotFoundException, BadRequestException {
        for (ProductStockRequestDTO dto:products) {
            var optional = repository.findById(dto.idProduct());
            if (optional.isEmpty()) {
                throw new NotFoundException("No hay un registro en la tabla Producto con el id: " + dto.idProduct());
            }
            var product = optional.get();
            if (product.getStock() < dto.stock()){
                throw new BadRequestException("La cantidad supera el stock disponible");
            }
            var newStock = product.getStock() - dto.stock();
            product.setStock(newStock);
            repository.save(product);
        }
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
        product.setStock(dto.stock());
        product.setDescription(dto.description());
        product.setQuantitySells(0);
        var subcategory = new SubCategory();
        subcategory.setId(dto.idSubcategory());
        product.setSubcategory(subcategory);

        var brand = new Brand();
        brand.setId(dto.idBrand());
        product.setBrand(brand);

        return product;
    }

    private ProductSalesDTO mapToDTOSales(Product product){
        return new ProductSalesDTO(
                product.getId(), product.getTitle(), product.getPrice(),
                product.getQuantitySells(),product.getSubcategory().getName(),
                product.getSubcategory().getCategory().getName(),product.getBrand().getName()
        );
    }

    private ProductSalesImageDTO mapToDTOSalesImage(Product product,List<ImageDTO> images){
        Set<ImageDTO> listDto = new HashSet<>();
        for (ImageDTO image: images) {
            listDto.add(new ImageDTO(image.id(), image.name(), image.url(), product.getId()));
        }
        return new ProductSalesImageDTO(
                product.getId(), product.getTitle(), product.getPrice(),product.getDescription(),
                product.getStock(), product.getQuantitySells(),
                listDto,product.getSubcategory().getName(),
                product.getSubcategory().getCategory().getName(),product.getBrand().getName()
        );
    }
    private ProductResponseDTO mapToDTO(Product product,List<ImageDTO> images){
        Set<ImageDTO> listDto = new HashSet<>();
        for (ImageDTO image: images) {
            listDto.add(new ImageDTO(image.id(), image.name(), image.url(), product.getId()));
        }
        return new ProductResponseDTO(
                product.getId(), product.getTitle(), product.getPrice(),
                product.getDescription(), product.getStock(), listDto,product.getSubcategory().getName(),
                product.getSubcategory().getCategory().getName(),product.getBrand().getName()
        );
    }

}
