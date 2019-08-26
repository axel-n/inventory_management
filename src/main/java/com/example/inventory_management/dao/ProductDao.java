package com.example.inventory_management.dao;

import com.example.inventory_management.models.product.Product;
import com.example.inventory_management.models.product.dict.BrandType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Page<Product> findByName(String name, Pageable pageable);
    Page<Product> findByBrand(BrandType brand, Pageable pageable);
    List<Product> findAll();

//    @Modifying
//    @Transactional
//    @Query("UPDATE Product c SET c.status = :status, c.assigner = :assigner, c.udat = CURRENT_TIMESTAMP WHERE c.id = :contractId")
//    int updateStatus(@Param("contractId") int contractId, @Param("assigner") User assigner, @Param("status") TaskStatus status);

    Optional<Product> findById(int contractId);
}

