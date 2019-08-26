package com.example.inventory_management.spring;

import com.example.inventory_management.spring.auth.SecurityContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final LocalAdminAuthenticationProvider localAdminAuthenticationProvider;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public SecurityConfig(LocalAdminAuthenticationProvider localAdminAuthenticationProvider, SecurityContextRepository securityContextRepository) {
        this.localAdminAuthenticationProvider = localAdminAuthenticationProvider;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(localAdminAuthenticationProvider)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/api/v1/login").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }
}
