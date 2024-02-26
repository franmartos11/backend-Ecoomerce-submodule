package com.example.user.repository;

import com.example.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;
import org.keycloak.admin.client.Keycloak;

import javax.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final Keycloak keycloakClient;

    @Value("${spring.security.oauth2.client.registration.keycloak.realm}")
    private String realm;

    public List<User> findAll() {
        return keycloakClient.realm(realm).users().list().stream().map(this::mapToUser).collect(Collectors.toList());
    }

    public boolean save(User user){
        keycloakClient.realm(realm).users().create(mapToRepresentation(user));

        return true;
    }

    public Optional<User> findById(String id) {
        UserRepresentation userRepresentation;
        try {
            userRepresentation = keycloakClient.realm(realm)
                    .users().get(id)
                    .toRepresentation();
        } catch (NotFoundException e) {
            return Optional.empty();
        }
        return Optional.of(mapToUser(userRepresentation));
    }


    private User mapToUser(UserRepresentation representation) {
        return User.builder()
                .id(representation.getId())
                .name(representation.getFirstName())
                .lastName(representation.getLastName())
                .email(representation.getEmail())
                .city(representation.firstAttribute("city"))
                .postalCode(representation.firstAttribute("postalCode"))
                .address(representation.firstAttribute("address"))
                .build();
    }

    private UserRepresentation mapToRepresentation(User user) {
        var representation = new UserRepresentation();
        return representation;
    }

}
