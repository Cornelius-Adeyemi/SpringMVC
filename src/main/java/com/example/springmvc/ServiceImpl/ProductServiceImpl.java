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
//@Service explains that this class holds business logic and implement the logic in the method body of
//methods implemented from the interface with almost correlating class names.
//NOTE: Declaring your methods in an interface and implementing them in a different class is an example of Strategy Design
// Pattern which a type of software Behavioural Design Pattern amongst many others....
//...so look up design patterns guys
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private UserRepository userRepository;
    @Autowired
    //The above annotation is explained in controller class, please go there to see comments
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    //@Override is just Java telling us that this method is derived from another class or interface
    //and that below is our own, personal, implementation of the method and the implementation we wish or desire to use
    // in context with this implementing class.
    public Product saveProduct(ProductDTO productDTO, Long id) {
        //upon recieving our productDTO and user's Id from the session
        //we create a product object using our custom constructor in our Product model class
        Product product = new Product(productDTO);
        //search for the user currently trying to save this object below
        Optional<User> user = userRepository.findById(id);
        //ternary operation to check if the query returned a user or null
        User existingUser = user.isPresent()?user.get():null;
        if(existingUser!=null) {
            //if our user exists, then add the user to our product's user field that is mapped @ManyToOne
            //so that the mapping of the product to the current user will be defined
            product.setUser(existingUser);
        }

        //Then save our product object and return
        return productRepository.save(product);
    }

    @Override
    //Our method below queries the product table in our database via our Repository interface and returns a List available products
    // belonging to the currently logged-in user adding this list as the constructor parameter for the PageImpl construct and return a Page object.
    // NOTE: PageImpl class and Page interface are Spring boot's inbuilt methods made to assist developers in
    // structuring data in a paginated structure for easy use in web applications for better user experience when our users
    // traverses through bulk data presented to them in the view or client side.
    public Page findAllByUserId(Long id) {
        return new PageImpl(productRepository.findByUserId(id));
    }

    @Override
    public List<Product> findAll() {
        //Spring jpa repository method to find all Data in DB
        //NOTE: THIS METHOD IS NOT USED AS WE DO NOT HAVE AN ADMIN (OR ANYONE) THAT WOULD REQUIRE OR HAS THE AUTHORITY TO
        // VIEW ALL PRODUCTS SOLD OR REGISTERED BY ALL THE USERS OUR THE APPLICATION.
        // WE BE AWA OWN ADMIN, WE DEY SHEK AM FROM PGADMIN UI.
        // *Humor is needed in programming, and also, remember to take a break guys. As I am about to do here in line 74. SYL..
        List<Product> productList =  productRepository.findAll();
        return productList;
    }

    @Override
    public Product findProduct(Long productId) {
        //Find a product using its provided ID
        return productRepository.findById(productId)
                //If the query fails to find any product with ID throw and exception with the message below
                .orElseThrow(()-> new NullPointerException("Product with id "+productId+" does not exist"));

        //.orElseThrow() is a method unique to Optional objects. Meaning any object stored in an Optional class, a "collection class"
        // found in Java's Util package ( like Optional<Product> in this scenario) can either use the get() method to retrieve
        //the data or use the .orElseThrow() method to retrieve the data or throw an exception in the case of "unavailable data" or
        //null
    }

    @Override
    public void deleteById(Long id) {
        //SIMPLY: delete from the product table any product that has a primary key that matches our Long id passed as Argument.
        //See Argument definition below for a trivial fact and a learning tip...

        productRepository.deleteById(id);
        //Argument in English is a presentation of facts or data or opinions in order to support a theory or idea.
        //In computing terms, arguments are set of data (or facts) that support a function's operation.
        //See a similarity between english language and programming terminologies?
        // Look for these similarities in concepts you find difficulty relating to.
    }
}
