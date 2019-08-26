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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/products/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Page<Product>> searchByParam(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "brand", required = false) BrandType brand,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestHeader HttpHeaders headers) {

        Pageable pageable = PageRequest.of(page, size);
        Claims claims = jwtUtil.getAllClaimsFromHeaders(headers);

        if (name != null) {
            log.info("user with claims {} want get products by name {}", claims, name);
            return Mono.just(productDao.findByNameEquals(name, pageable));
        }

        if (brand != null) {
            log.info("user with claims {} want get products by brand {}", claims, brand);
            return Mono.just(productDao.findByBrand(brand, pageable));
        }


        log.warn("not found any param");
        return Mono.empty();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Long> addProduct(@RequestBody Product product,
                                 @RequestHeader HttpHeaders headers) {

        log.info("addProduct test");

        if (product.getCount() == null) {
            product.setCount(1L);
        }

        Claims claims = jwtUtil.getAllClaimsFromHeaders(headers);
        log.info("user with claims {} want create product {}", claims, product);

        product = productDao.save(product);
        if (product.getId() != null) {
            return Mono.just(product.getId());
        } else {
            log.error("not create product");
            return Mono.empty();
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Boolean> addProduct(@PathVariable("productId") Long productId,
                                    @RequestHeader HttpHeaders headers) {

        Claims claims = jwtUtil.getAllClaimsFromHeaders(headers);
        log.info("user with claims {} want delete productId {}", claims, productId);

        productDao.deleteById(productId);

        if (productDao.findById(productId).isEmpty()) {
            return Mono.just(true);
        } else {
            return Mono.just(false);
        }
    }

    // for test
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<List<Product>> getAllProducts() {
        return Mono.just(productDao.findAll());
    }
}
