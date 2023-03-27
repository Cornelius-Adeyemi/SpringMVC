package com.example.springmvc.DTO;

import com.example.springmvc.Model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String productName;
    private String category;
    private BigDecimal price;
    public ProductDTO(Product product) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price =  product.getPrice();
    }

}
