package com.grupo5.vinylSound.catalog.service;

import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Category;
import com.grupo5.vinylSound.catalog.model.dto.category.CategoryDTO;
import com.grupo5.vinylSound.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public void create(CategoryDTO dto) throws BadRequestException {
        if (repository.findByName(dto.name()).isPresent()) {
            throw new BadRequestException("Ya existe una categoria con el nombre: " + dto.name());
        }
        repository.save(mapToCategory(new CategoryDTO(null,dto.name())));
    }

    public List<CategoryDTO> getAll(){
        var categories = repository.findAll();
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }

        List<CategoryDTO> listDTO = new ArrayList<>();
        for (Category category: categories) {
            listDTO.add(mapToDTO(category));
        }
        return listDTO;
    }

    public CategoryDTO getById(Long id) throws NotFoundException {
        var optionalCategory = repository.findById(id);

        if (optionalCategory.isEmpty()){
            throw new NotFoundException("No hay registro de categoria con el id: " + id);
        }
        return mapToDTO(optionalCategory.get());
    }

    public void update(CategoryDTO dto) throws NotFoundException, BadRequestException {
        var category = repository.findById(dto.id());
        if (category.isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Categoria con el id: " + dto.id());
        }

        if (!dto.name().equals(category.get().getName())){
            if (repository.findByName(dto.name()).isPresent()){
                throw new BadRequestException("Ya existe una categoria con el titulo: " + dto.name());
            }
        }

        repository.save(mapToCategory(dto));
    }

    public void deleteById(Long id) throws NotFoundException {
        var category = repository.findById(id);
        if(category.isEmpty())
            throw new NotFoundException("La categoria no fue encontrada en la base de datos");

        repository.deleteById(id);
    }

    private CategoryDTO mapToDTO(Category category){
        return new CategoryDTO(category.getId(),category.getName());
    }
    private Category mapToCategory(CategoryDTO dto) {
        var category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        return category;
    }
}
