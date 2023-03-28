package com.example.springmvc;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Model.User;
import com.example.springmvc.Repository.ProductRepository;
import com.example.springmvc.Repository.UserRepository;
import com.example.springmvc.Services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @Autowired
    public ProductServiceImplTest(ProductRepository productRepository, UserRepository userRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Test
    public void testSaveProduct(){
        User user = new User();
        user.setUsername("myUsername@gmail.com");
        user.setPassword("somestuffIlikeasPASSWORD");
        user.setSalt("jhgkjhkjhkj");
        user.setId(1L);
        Product product = new Product();
        product.setProductName("macbook");
        product.setCategory("Electronics");
        product.setPrice(BigDecimal.valueOf(1234.3));
        product.setId(2L);
        product.setUser(user);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setProductName(product.getProductName());

        //mock of what happens in our productService.saveProduct() method
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(productService.saveProduct(productDTO, user.getId())).thenReturn(product);

        Assertions.assertEquals(product, productService.saveProduct(productDTO, user.getId()));
    }
}
