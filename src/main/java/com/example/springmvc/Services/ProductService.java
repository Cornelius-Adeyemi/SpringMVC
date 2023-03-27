package com.example.springmvc.Services;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;

import java.util.List;

public interface ProductService {
 Product saveProduct(ProductDTO productDTO);
 List<Product> findAll();
}
