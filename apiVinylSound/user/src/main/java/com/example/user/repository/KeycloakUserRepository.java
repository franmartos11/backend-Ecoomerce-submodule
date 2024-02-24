package com.example.user.repository;

import com.example.user.domain.User;
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
public class KeycloakUserRepository{
    private final Keycloak keycloakClient;

    @Value("${spring.security.oauth2.client.registration.keycloak.realm}")
    private String realm;

    private User toUser(UserRepresentation userRepresentation) {
        return User.builder()
                .id(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .build();
    }

    public List<User> findAll() {
        return keycloakClient.realm(realm).users().list().stream().map(this::toUser).collect(Collectors.toList());
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
        return Optional.of(toUser(userRepresentation));
    }

}
