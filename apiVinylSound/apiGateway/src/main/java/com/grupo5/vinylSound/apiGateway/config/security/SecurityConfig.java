package com.grupo5.vinylSound.apiGateway.config.security;

import com.grupo5.vinylSound.apiGateway.security.KeyCloakJwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
    // SWAGGER
    "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
    "/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**",
    "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html",
  };

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
    http
      .authorizeExchange()
      .pathMatchers(AUTH_WHITELIST).permitAll()
      .pathMatchers("/api/**").permitAll()
      .pathMatchers("/admin/**").hasAuthority("ROLE_admin")
      .pathMatchers("/user/**").hasAnyAuthority("ROLE_user", "ROLE_admin")
      .anyExchange().authenticated()
      .and()
      .csrf().disable()
      .cors().configurationSource(request -> corsConfiguration())
      .and()
      .oauth2ResourceServer()
      .jwt()
      .jwtAuthenticationConverter(grantedAuthoritiesExtractor());
    return http.build();
  }

  @Bean
  public CorsConfiguration corsConfiguration() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.addAllowedOriginPattern("http://localhost:5173");
    corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PUT","OPTIONS","PATCH","DELETE"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setExposedHeaders(List.of("Authorization"));
    return corsConfiguration;
  }

  @Bean
  public JwtDecoder jwtDecoder(){
    return NimbusJwtDecoder.withJwkSetUri("http://localhost:8181/realms/vinylSound/protocol/openid-connect/certs").build();
  }

  Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakJwtConverter());
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }

}