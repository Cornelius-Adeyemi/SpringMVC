package com.example.springmvc.Model;


import com.example.springmvc.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.BitSet;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_names")
    private String productName;
    private String category;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Product(ProductDTO productDTO) {
        this.productName = productDTO.getProductName();
        this.category = productDTO.getCategory();
        this.price =  productDTO.getPrice();
    }

    public Product() {

    }
}
