package dev.java.ecommerce.basketservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductModel {

    private Long id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
