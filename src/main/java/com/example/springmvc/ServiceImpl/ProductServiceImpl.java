package com.example.springmvc.ServiceImpl;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Model.User;
import com.example.springmvc.Repository.ProductRepository;
import com.example.springmvc.Repository.UserRepository;
import com.example.springmvc.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        Optional<User> user = userRepository.findById(id);
        User existingUser = user.isPresent()?user.get():null;
        product.setUser(existingUser);
        return productRepository.save(product);
    }

    @Override
    public Page findAllByUserId(Long id) {
        return new PageImpl(productRepository.findByUserId(id));
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList =  productRepository.findAll();
        return productList;
    }

    @Override
    public Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new NullPointerException("Product with id "+productId+" does not exist"));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
