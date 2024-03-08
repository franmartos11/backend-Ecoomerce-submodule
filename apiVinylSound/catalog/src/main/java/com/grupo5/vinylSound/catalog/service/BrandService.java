package com.grupo5.vinylSound.catalog.service;


import com.grupo5.vinylSound.catalog.exception.BadRequestException;
import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Brand;
import com.grupo5.vinylSound.catalog.model.dto.brand.BrandDTO;
import com.grupo5.vinylSound.catalog.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository repository;

    public void create(BrandDTO dto) throws BadRequestException {
        if (repository.findByName(dto.name()).isPresent()) {
            throw new BadRequestException("Ya existe una marca con el nombre: " + dto.name());
        }
        repository.save(mapToBrand(new BrandDTO(null,dto.name(), dto.url())));
    }

    public List<BrandDTO> getAll(){
        var brands = repository.findAll();
        if (brands.isEmpty()) {
            return Collections.emptyList();
        }

        List<BrandDTO> listDTO = new ArrayList<>();
        for (Brand brand: brands) {
            listDTO.add(mapToDTO(brand));
        }
        return listDTO;
    }

    public BrandDTO getById(Long id) throws NotFoundException {
        var optionalBrand = repository.findById(id);

        if (optionalBrand.isEmpty()){
            throw new NotFoundException("No hay registro de marca con el id: " + id);
        }
        return mapToDTO(optionalBrand.get());
    }

    public void update(BrandDTO dto) throws NotFoundException, BadRequestException {
        var brand = repository.findById(dto.id());
        if (brand.isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Marca con el id: " + dto.id());
        }

        if (!dto.name().equals(brand.get().getName())){
            if (repository.findByName(dto.name()).isPresent()){
                throw new BadRequestException("Ya existe una marca con el titulo: " + dto.name());
            }
        }

        repository.save(mapToBrand(dto));
    }

    public void deleteById(Long id) throws NotFoundException {
        var brand = repository.findById(id);
        if(brand.isEmpty())
            throw new NotFoundException("La marca no fue encontrada en la base de datos");

        repository.deleteById(id);
    }

    private BrandDTO mapToDTO(Brand brand){
        return new BrandDTO(brand.getId(),brand.getName(), brand.getUrl());
    }
    private Brand mapToBrand(BrandDTO dto) {
        var brand = new Brand();
        brand.setId(dto.id());
        brand.setName(dto.name());
        brand.setUrl(dto.url());
        return brand;
    }
}
