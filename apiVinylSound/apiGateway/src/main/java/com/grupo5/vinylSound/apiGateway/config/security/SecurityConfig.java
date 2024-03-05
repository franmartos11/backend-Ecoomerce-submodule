package com.grupo5.vinylSound.apiGateway.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;

    private static final String[] AUTH_WHITELIST = {
            //  Swagger
            "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
            "/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**",
            "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html",
            // other public endpoints of your API may be appended to this array
            "/catalog/brand/all","/catalog/brand/{id}",
            "/catalog/category/all","/catalog/category/{id}",
            "/catalog/product/all","/catalog/product/{id}",
            "/catalog/product/allfav","/catalog/product/category={categoryId}",
            "/catalog/product/subcategory={subcategoryId}", "/catalog/product/brand={brandId}",
            "/catalog/subcategory/all","/catalog/subcategory/{id}",
            "/user/create","/user/forgot-password/{email}",
    };
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessHandler(oidcServerLogoutSuccessHandler());
        return http.build();
    }


    private ServerLogoutSuccessHandler oidcServerLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler
                = new OidcClientInitiatedServerLogoutSuccessHandler(reactiveClientRegistrationRepository);
        oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/login");
        return oidcClientInitiatedLogoutSuccessHandler;
    }
}
