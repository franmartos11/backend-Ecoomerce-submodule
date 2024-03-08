package com.grupo5.vinylSound.catalog.service;

import com.grupo5.vinylSound.catalog.exception.NotFoundException;
import com.grupo5.vinylSound.catalog.model.Image;
import com.grupo5.vinylSound.catalog.model.Product;
import com.grupo5.vinylSound.catalog.model.dto.image.ImageProductDTO;
import com.grupo5.vinylSound.catalog.model.dto.image.ImageDTO;
import com.grupo5.vinylSound.catalog.repository.ImageRepository;
import com.grupo5.vinylSound.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository repository;
    private final ProductRepository productRepository;

    public void createImage(ImageDTO dto){
        var persist = mapToImage(dto);
        repository.save(persist);
    }

    public List<ImageDTO> getAllByIdProduct(Long idProduct){
        var images = repository.findByProductId(idProduct);
        if (images.isEmpty()) {
            return Collections.emptyList();
        }

        List<ImageDTO> listDTO = new ArrayList<>();
        for (Image image: images) {
            listDTO.add(mapToDTO(image));
        }
        return listDTO;
    }

    public List<ImageDTO> getAllByTitleProduct(String title){
        var images = repository.findByProductTitle(title);
        if (images.isEmpty()) {
            return Collections.emptyList();
        }

        List<ImageDTO> listDTO = new ArrayList<>();
        for (Image image: images) {
            listDTO.add(mapToDTO(image));
        }
        return listDTO;
    }

    public void update(ImageProductDTO dto, Long idProduct) throws NotFoundException {
        var product = productRepository.findById(idProduct);
        if (product.isEmpty()) {
            throw new NotFoundException("No hay un registro en la tabla Producto con el id: " + idProduct);
        }
        repository.save(mapToImage(new ImageDTO(dto.id(), dto.name(), dto.url(), idProduct)));
    }

    public void deleteById(Long id) throws NotFoundException {
        var image = repository.findById(id);
        if(image.isEmpty())
            throw new NotFoundException("La imagen no fue encontrada en la base de datos");

        repository.deleteById(id);
    }

    private ImageDTO mapToDTO(Image image) {
        return new ImageDTO(image.getId(),image.getName(), image.getUrl(),image.getProduct().getId());
    }

    private Image mapToImage(ImageDTO dto){
        Image image = new Image();
        image.setId(dto.id());
        image.setName(dto.name());
        image.setUrl(dto.url());
        Product product = new Product();
        product.setId(dto.idProduct());
        image.setProduct(product);
        return image;
    }
}
