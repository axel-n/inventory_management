package com.example.inventory_management.controllers;

import com.example.inventory_management.dao.ProductDao;
import com.example.inventory_management.dao.UserDao;
import com.example.inventory_management.models.product.Product;
import com.example.inventory_management.models.product.dict.BrandType;
import com.example.inventory_management.spring.auth.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

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
    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Page<Product>> searchByParam(@RequestBody Map<String, Object> params,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestHeader HttpHeaders headers) {

        Object name = params.get("name");
        Object brand = params.get("brand");
        Pageable pageable = PageRequest.of(page, size);
        Claims claims = jwtUtil.getAllClaimsFromHeaders(headers);

        if (name != null) {
            log.info("user with claims {} want get products by name {}", claims, name);
            return Mono.just(productDao.findByName(name.toString(), pageable));
        }

        if (brand != null) {
            BrandType brandType = BrandType.valueOf(brand.toString());
            log.info("user with claims {} want get products by brand {}", claims, brandType);
            return Mono.just(productDao.findByBrand(brandType, pageable));
        }

        log.warn("not found any param");
        return Mono.empty();
    }

        // for test
        @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public Mono<List<Product>> getAllProducts () {

            return Mono.just(productDao.findAll());
        }
    }
