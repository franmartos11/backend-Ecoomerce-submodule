package com.grupo5.vinylSound.catalog.service;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Category;
import com.grupo5.vinylSound.catalog.model.SubCategory;
import com.grupo5.vinylSound.catalog.model.dto.subcategory.SubCategoryDTO;
import com.grupo5.vinylSound.catalog.repository.CategoryRepository;
import com.grupo5.vinylSound.catalog.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubCategoryService {
    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;

    public void create(SubCategoryDTO dto) throws BadRequestException {
        if(repository.findByName(dto.name()).isPresent()){
            throw new BadRequestException("Ya existe una subcategoria con el nombre: " + dto.name());
        }

        if (dto.idCategory() == null){
            throw new BadRequestException("El id de categoria no puede ser nulo");
        }
        if (categoryRepository.findById(dto.idCategory()).isEmpty()){
            throw new BadRequestException("No existe una categoria con el id: " + dto.idCategory());
        }

        repository.save(
                mapToSubCategory(
                        new SubCategoryDTO(null,dto.name(), dto.idCategory())));
    }

    public List<SubCategoryDTO> getAll(){
        var subCategories = repository.findAll();
        if (subCategories.isEmpty()) {
            return Collections.emptyList();
        }

        List<SubCategoryDTO> listDTO = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            listDTO.add(mapToDTO(subCategory));
        }
        return listDTO;
    }

    public SubCategoryDTO getById(Long id) throws NotFoundException {
        var optionalSubCategory = repository.findById(id);

        if (optionalSubCategory.isEmpty()){
            throw new NotFoundException("No hay registro de SubCategoria con el id: " + id);
        }
        return mapToDTO(optionalSubCategory.get());
    }

    public void update(SubCategoryDTO dto) throws NotFoundException, BadRequestException {
        var subcategory = repository.findById(dto.id());
        if (subcategory.isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla SubCategoria con el id: " + dto.id());
        }

        if (categoryRepository.findById(dto.idCategory()).isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Categoria con el id: " + dto.idCategory());
        }

        if (!dto.name().equals(subcategory.get().getName())){
            if (repository.findByName(dto.name()).isPresent()){
                throw new BadRequestException("Ya existe una subcategoria con el titulo: " + dto.name());
            }
        }

        repository.save(mapToSubCategory(dto));
    }

    public void deleteById(Long id) throws NotFoundException {
        var subcategory = repository.findById(id);
        if(subcategory.isEmpty())
            throw new NotFoundException("La subcategoria no fue encontrada en la base de datos");

        repository.deleteById(id);
    }

    private SubCategory mapToSubCategory(SubCategoryDTO dto){
        SubCategory subCategory = new SubCategory();
        subCategory.setId(dto.id());
        subCategory.setName(dto.name());

        Category category = new Category();
        category.setId(dto.idCategory());
        subCategory.setCategory(category);

        return subCategory;
    }

    private SubCategoryDTO mapToDTO(SubCategory subCategory){
        return new SubCategoryDTO(
                subCategory.getId(), subCategory.getName(), subCategory.getCategory().getId()
        );
    }
}
