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
//Annotating a spring boot application test class
public class ProductServiceImplTest {
    @Mock
    //Declaring beans to be created for our test class
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    //Declaring beans that need dependent "mock" beans injected into. Meaning objects that will need to access the beans of the
    //"mock" annotated classes.
    private ProductService productService;

    @Autowired
    //Declare constructor dependency injection for our bean
    public ProductServiceImplTest(ProductRepository productRepository, UserRepository userRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Test
    //Annotating our test methods
    public void testSaveProduct(){
        //To save a product we also need a user object to map the product
        User user = new User();
        user.setUsername("myUsername@gmail.com");
        user.setPassword("somestuffIlikeasPASSWORD");
        user.setSalt("jhgkjhkjhkj");
        user.setId(1L);

        //Creating a product object to use in our test and to use to assert if the test method's return value in the same as the
        //stored or saved product.
        Product product = new Product();
        product.setProductName("macbook");
        product.setCategory("Electronics");
        product.setPrice(BigDecimal.valueOf(1234.3));
        product.setId(2L);
        product.setUser(user);

        //creating a productDTO since our saveProduct() requires it to save a methods
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory());
        productDTO.setProductName(product.getProductName());

        //mock what happens in our productService.saveProduct() method
        //Describing expected result of every method call done by the saveProduct()
        //1st call is to find the user saving a Product
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        //2nd method call is to save our product with our repository save()
        Mockito.when(productRepository.save(product)).thenReturn(product);
        //last mock method call represent the description of the expected outcome of calling saveProduct() method
        Mockito.when(productService.saveProduct(productDTO, user.getId())).thenReturn(product);
        //Once we've mocked all method calls and their response we can see if our saveProduct() method actually works as we
        //declared with our Mockito.when().thenReturn() and check if the return is the same as our product data.
        Assertions.assertEquals(product, productService.saveProduct(productDTO, user.getId()));
    }
}
