package com.grupo5.vinylSound.user.repository;

import com.grupo5.vinylSound.user.exception.BadRequestException;
import com.grupo5.vinylSound.user.exception.InternalServerErrorException;
import com.grupo5.vinylSound.user.exception.NotFoundException;
import com.grupo5.vinylSound.user.model.User;
import com.grupo5.vinylSound.user.model.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;
import org.keycloak.admin.client.Keycloak;


import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final Keycloak keycloakClient;
    @Value("${spring.security.oauth2.client.registration.keycloak.realm}")
    private String realm;

    public void save(User user) throws BadRequestException, InternalServerErrorException {
        var userResource = getUsersResource();
        var roleResource = getRolesResource();

        var email = userResource.searchByEmail(user.getEmail(),true);
        if (email.iterator().hasNext()) {
            throw new BadRequestException("Ya existe un usuario con el email: " + user.getEmail());
        }

        var rol = roleResource.get("user").toRepresentation();
        Response response;
        try {
            response = userResource.create(mapToRepresentation(user));
            System.out.println("creo el usuario");
        } catch (Exception e) {
            throw new InternalServerErrorException("Se ha producido un error al crear el nuevo usuario: " + user.getEmail());
        }

        var path = response.getLocation().getPath();
        System.out.println("busco el path");
        var userId = path.substring(path.lastIndexOf("/") + 1);
        System.out.println("busco el user id");

        if (response.getStatus() ==201){
            System.out.println("verifico codigo de respuesta");
            userResource.get(userId).roles().realmLevel().add(List.of(rol));
            System.out.println("quiero enviar el email");
            //userResource.get(userId).sendVerifyEmail();
            System.out.println("envio el email");
        }else {
            throw new InternalServerErrorException("Se ha producido un error 2 al crear el nuevo usuario: " + user.getEmail());
        }
    }

    public List<UserResponseDTO> findAll() {
        return getUsersResource().list()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> findById(String id) {
        UserRepresentation user;
        try {
            user = getUsersResource().get(id).toRepresentation();
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(mapToDTO(user));
    }

    public void edit(User user) throws NotFoundException {
        var usersResource =keycloakClient.realm(realm).users();

        try {
            usersResource.get(user.getId()).update(mapToEditRepresentation(user));
        }catch (Exception e){
            throw new NotFoundException("No se encontro ningun usuario con el id: " + user.getId());
        }
    }

    public void delete(String id){
        getUsersResource().delete(id);
    }

    public void forgotPassword(String email) throws NotFoundException {
        var userResource = getUsersResource();
        var representationList = userResource.searchByEmail(email,true);

        var representation = representationList.stream().findFirst().orElse(null);
        if (representation == null){
            throw new NotFoundException("El usuario con el email: " + email + " no se encontro");
        }
        var user = userResource.get(representation.getId());
        user.executeActionsEmail(List.of("UPDATE_PASSWORD"));

    }

    public void resetPassword(String password,String id) throws NotFoundException {
        try {
            var credentials = new CredentialRepresentation();
            credentials.setTemporary(false);
            credentials.setType(OAuth2Constants.PASSWORD);
            credentials.setValue(password);
            getUsersResource().get(id).resetPassword(credentials);
        }catch (Exception e){
            throw new NotFoundException("No se encontro el usuario con el id: " + id);
        }
    }

    private UserResponseDTO mapToDTO(UserRepresentation representation) {
        return new UserResponseDTO(representation.getId(),representation.getFirstName(),representation.getLastName(),
                representation.getEmail(),representation.firstAttribute("city"),representation.firstAttribute("postalCode"),
                representation.firstAttribute("address"));
    }

    private UserRepresentation mapToRepresentation(User user) {
        var representation = new UserRepresentation();

        representation.setFirstName(user.getName());
        representation.setLastName(user.getLastName());
        representation.setEmail(user.getEmail());
        representation.setEmailVerified(user.getIsEmailVerified());
        if(!user.getIsEmailVerified()){
            representation.setRequiredActions(List.of("VERIFY_EMAIL"));
        }
        representation.setEnabled(true);

        var password = new CredentialRepresentation();
        password.setTemporary(false);
        password.setType(OAuth2Constants.PASSWORD);
        password.setValue(user.getPassword());
        representation.setCredentials(List.of(password));


        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("city", Collections.singletonList(user.getCity()));
        attributes.put("postalCode", Collections.singletonList(user.getPostalCode()));
        attributes.put("address", Collections.singletonList(user.getAddress()));
        representation.setAttributes(attributes);

        return representation;
    }

    private UserRepresentation mapToEditRepresentation(User user) {
        var representation = new UserRepresentation();

        representation.setFirstName(user.getName());
        representation.setLastName(user.getLastName());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("city", Collections.singletonList(user.getCity()));
        attributes.put("postalCode", Collections.singletonList(user.getPostalCode()));
        attributes.put("address", Collections.singletonList(user.getAddress()));
        representation.setAttributes(attributes);

        return representation;
    }

    public UsersResource getUsersResource(){
        return keycloakClient.realm(realm).users();
    }

    public RolesResource getRolesResource(){
        return keycloakClient.realm(realm).roles();
    }

}
