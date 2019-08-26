package com.example.inventory_management.controllers;

import com.example.inventory_management.dao.ProductDao;
import com.example.inventory_management.dao.UserDao;
import com.example.inventory_management.models.product.Product;
import com.example.inventory_management.spring.auth.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final JWTUtil jwtUtil;

    public ProductController(JWTUtil jwtUtil, ProductDao contractDao, UserDao userDao) {
        this.jwtUtil = jwtUtil;
        this.productDao = contractDao;
        this.userDao = userDao;
    }

    // for test
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<List<Product>> getAllProducts() {

        return Mono.just(productDao.findAll());
    }
}
