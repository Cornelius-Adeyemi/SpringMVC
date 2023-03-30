package com.example.springmvc.DTO;

import com.example.springmvc.Model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
/*
A product DTO Class to help transfer data from the Product class to be delivered to the user on the view
as well as to recieve data from the user and deliver to the Product object for delivery to the database.
 */
public class ProductDTO {
    private Long id;
    private String productName;
    private String category;
    private BigDecimal price;
    public ProductDTO(Product product) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price =  product.getPrice();
    }

}
