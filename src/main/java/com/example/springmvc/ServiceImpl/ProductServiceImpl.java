package com.example.springmvc.ServiceImpl;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Model.User;
import com.example.springmvc.Repository.ProductRepository;
import com.example.springmvc.Repository.UserRepository;
import com.example.springmvc.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Product saveProduct(ProductDTO productDTO, Long id) {
        Product product = new Product(productDTO);
        User user = userRepository.findById(id).get();
        product.setUser(user);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByUserId(Long id) {
        return productRepository.findByUserId(id);
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList =  productRepository.findAll();
        return productList;
    }
}
