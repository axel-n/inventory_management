package com.example.inventory_management.models.product;

import com.example.inventory_management.models.product.dict.BrandType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NotNull
    private Long count;

    @NotNull
    private BrandType brand;

    @NotNull
    private BigDecimal price;

    // created
    @NotNull
    private Date cdat;

    // updated
    private Date udat;

    public static Product merge(Product p1, Product p2) throws Exception {

        Product merged = new Product();

        for (Field field : Product.class.getDeclaredFields()) {
            field.set(merged, field.get(p1) != null? field.get(p1) : field.get(p2));
        }

        return merged;
    }
}
