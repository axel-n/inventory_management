package com.example.inventory_management;

import com.example.inventory_management.dao.ProductDao;
import com.example.inventory_management.dao.UserDao;
import com.example.inventory_management.models.product.Product;
import com.example.inventory_management.models.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// for test
@Slf4j
@Component
public class DemoData implements CommandLineRunner {

    private final ProductDao productDao;
    private final UserDao userDao;
    private final ObjectMapper OM = new ObjectMapper();

    @Autowired
    public DemoData(ProductDao productDao, UserDao userDao) {
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public void run(String... args) throws JsonProcessingException {

        log.info("all users");
        List<User> users = userDao.findAll();
        log.info("size users {}", users.size());
        log.info("users {}",  OM.writeValueAsString(users));

        log.info("all products");
        List<Product> products = productDao.findAll();
        log.info("size products {}", products.size());
        log.info("products {}",  OM.writeValueAsString(products));
    }
}
