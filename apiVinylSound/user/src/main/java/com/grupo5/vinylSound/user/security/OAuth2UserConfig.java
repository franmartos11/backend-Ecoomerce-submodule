package com.grupo5.vinylSound.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2UserConfig {
    private static final String[] AUTH_WHITELIST = {
            //  Swagger
            "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**",
            "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html",
            // other public endpoints of your API may be appended to this array
            "/user/create","/user/forgot-password/{email}",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                    .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(new KeyCloakJwtConverter());

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8181/realms/vinylSound/protocol/openid-connect/certs").build();
    }

}