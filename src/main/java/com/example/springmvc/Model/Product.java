package com.example.springmvc.Model;


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

}
