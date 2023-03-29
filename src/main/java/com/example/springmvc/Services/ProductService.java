package com.example.springmvc.Services;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
 Product saveProduct(ProductDTO productDTO, Long id);

 Page findAllByUserId(Long id);
 List<Product> findAll();

 Product findProduct(Long productId);

 void deleteById(Long id);
}
