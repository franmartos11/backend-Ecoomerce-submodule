package com.grupo5.vinylSound.user.service;

import com.grupo5.vinylSound.user.exception.BadRequestException;
import com.grupo5.vinylSound.user.exception.InternalServerErrorException;
import com.grupo5.vinylSound.user.exception.NotFoundException;
import com.grupo5.vinylSound.user.model.User;
import com.grupo5.vinylSound.user.model.dto.user.UserEditDTO;
import com.grupo5.vinylSound.user.model.dto.user.UserRequestDTO;
import com.grupo5.vinylSound.user.model.dto.user.UserResponseDTO;
import com.grupo5.vinylSound.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void create(UserRequestDTO dto) throws BadRequestException, InternalServerErrorException {
        if(dto.email() == null || dto.email().isEmpty()){
            throw new BadRequestException("El email no puede estar vacio.");
        }

        if(dto.password() == null || dto.password().isEmpty()){
            throw new BadRequestException("La contraseña no puede estar vacio.");
        }

        repository.save(mapToUser(dto));
    }

    public List<UserResponseDTO> findAll(){
        return repository.findAll();
    }

    public UserResponseDTO getById(String id) throws NotFoundException {
        var user = repository.findById(id);

        if (user.isEmpty()){
            throw new NotFoundException("No hay registro de usuario con el id: " + id);
        }
        return user.get();
    }

    public void edit(UserEditDTO dto) throws BadRequestException, NotFoundException {
        if(dto.address() == null || dto.address().isEmpty()){
            throw new BadRequestException("La dirección no puede estar vacio.");
        }

        if(dto.city() == null || dto.city().isEmpty()){
            throw new BadRequestException("La ciudad no puede estar vacio.");
        }

        if(dto.postalCode() == null || dto.postalCode().isEmpty()){
            throw new BadRequestException("El codigo postal no puede estar vacio.");
        }
        repository.edit(mapToUserEdit(dto));
    }

    public void deleteById(String id) throws NotFoundException {
        var optional = repository.findById(id);
        if(optional.isEmpty())
            throw new NotFoundException("El usuario no fue encontrada en la base de datos");

        repository.delete(id);
    }

    public void forgotPassword(String email) throws NotFoundException {
        repository.forgotPassword(email);
    }

    public void resetPassword(String password,String id) throws NotFoundException {
        repository.resetPassword(password,id);
    }
    private User mapToUser(UserRequestDTO dto) {
        var user = new User();
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setIsEmailVerified(false);
        user.setPassword(dto.password());
        user.setCity(dto.city());
        user.setPostalCode(dto.postalCode());
        user.setAddress(dto.address());
        return user;
    }

    private User mapToUserEdit(UserEditDTO dto) {
        var user = new User();
        user.setId(dto.id());
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setCity(dto.city());
        user.setPostalCode(dto.postalCode());
        user.setAddress(dto.address());
        return user;
    }
}
