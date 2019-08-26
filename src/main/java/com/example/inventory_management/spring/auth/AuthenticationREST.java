package com.example.inventory_management.spring.auth;

import com.example.inventory_management.spring.LocalAdminAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthenticationREST {

    private final JWTUtil jwtUtil;
    private final LocalAdminAuthenticationProvider localAdminAuthenticationProvider;
    private final ObjectMapper OM = new ObjectMapper();

    public AuthenticationREST(JWTUtil jwtUtil, LocalAdminAuthenticationProvider localAdminAuthenticationProvider) {
        this.jwtUtil = jwtUtil;
        this.localAdminAuthenticationProvider = localAdminAuthenticationProvider;
    }


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Map<String, Object>> login(@RequestBody AuthRequest ar,
                                           ServerHttpResponse httpResponse) {

        Map<String, Object> response = new HashMap<>();

        return localAdminAuthenticationProvider.authenticate(ar.getUsername(), ar.getPassword())
                .map(user -> {
                    response.put("role", user.getRole());
                    response.put("name", user.getName());

                    return user;
                })
                .map(jwtUtil::generateToken)
                .map(token -> {
                    response.put("token", token);
                    return response;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return Mono.empty();
                }))
                .onErrorResume(e -> {
                    httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.error("e: {}", e.getMessage());
                    return Mono.empty();
                });

    }

}
