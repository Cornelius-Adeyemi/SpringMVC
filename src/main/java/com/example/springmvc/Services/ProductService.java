package com.example.springmvc.Services;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//Strategy design pattern
//Declaring methods in an interface to be implemented by a class or various class where their implementation will be defined
//based on the class' required functionality. See the ServiceImpl package or the Implementation classes
//discover the functionality of the methods defined in the interface of our application.
public interface ProductService {
 Product saveProduct(ProductDTO productDTO, Long id);

 Page findAllByUserId(Long id);
 List<Product> findAll();

 Product findProduct(Long productId);

 void deleteById(Long id);
}
