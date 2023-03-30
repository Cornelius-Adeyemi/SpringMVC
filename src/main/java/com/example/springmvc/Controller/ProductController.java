package com.example.springmvc.Controller;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Repository.ProductRepository;
import com.example.springmvc.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class ProductController {


    //productService bean is declared for Injection in the ProductController class
    private ProductService productService;
    private ProductRepository productRepository;


    //Constructor injection for injecting dependent bean objects into this class.
    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    //Get HTTP Request method to path "http://localhost:8080/products/"
    @GetMapping("/products/")
    public ModelAndView homePageWithMV(@RequestParam(defaultValue = "0") int page,  ModelAndView modelAndView, HttpServletRequest httpServletRequest){
       //getting any available session in the instance of the application served to the user through multithreading. So no
        //two clients can be on the same instance of an application. Thanks to tomcat and Servlets.
        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("usernumber")==null){

            //if no session available then session is null,
            //and the user gets returned to the view login.html to login
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //if a session exists and a user in loggedin, find the users paginated products using their id stored in the session
        Page<Product> products = productService.findAllByUserId((Long)session.getAttribute("usernumber"));

        //set the contents of the paginated products in a PageListHolder
        // to enable us set the page size(number of elements per page) and
        //the page number to display to the view
        PagedListHolder<Product> productsPage = new PagedListHolder<>(products.getContent());

        //set page number and this will retrieve list of data on this page number
        //since pagination sections a collection of data by...
        productsPage.setPage(page);

        //...the size of the page that is set here,
        // set the page size(5 products per page)
        productsPage.setPageSize(5);

        //set the view "home" (home.html) to be returned
        modelAndView.setViewName("home");

        //add List of products object for the setPage with the name "products"
        modelAndView.addObject("products", productsPage.getPageList());

        //add current page value for indications in our view
        modelAndView.addObject("currentPage", productsPage.getPage());

        //add max number of pages to display
        modelAndView.addObject("totalPages", productsPage.getMaxLinkedPages());

        //return to home.html with; list of products, current page, and max no. of pages
        return modelAndView;
    }
    //Get HTTP Request method to path "http://localhost:8080/edit"

    @GetMapping("/edit")
    public ModelAndView editProduct(@RequestParam("id") Long id){
        Long productId = Long.valueOf(id);
        ProductDTO productDTO = new ProductDTO(productService.findProduct(productId));
        productDTO.setId(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", productDTO);
        modelAndView.setViewName("edit_product");
        return modelAndView;
    }

    @PostMapping("/edit-products")
    public String editProducts(@ModelAttribute(name = "product") ProductDTO productDTO){

        //@Slf4j allows us log info, errors and warnings in our console.
        log.info("Product id: --->"+productDTO.getId());
        Product product = productService.findProduct(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);

        //redirect user to the path "http://localhost:8080/products/ to handle the finalising operation of
        //the user's post request to the path /edit-products our the url "http://localhost:8080/edit-products"
        //see our the URL defines the communication as http: and the host as localhost and data location as /edit-products
        return "redirect:/products/";

    }
    @GetMapping("/add-products")
    public String productForm(Model model, HttpServletRequest httpServletRequest){
        //retrieving the session object (NOTE: Once a session is set it has it's own unique ID which once a user's UNIQUE data
        //like ID, username, or email is stored to the session allows the access to that data across the application and the
        //session's id therefor make's  the data only avaliable to user of the unique session.
        //So below try to get the unique session and if the user is not logged in...
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            //...the session will return null and the view to reteun to will be login.html to alert the user they need to login
            return "login";
        }
        //otherwise if a user is logged in (meaning we created a session and the session is not null)
        // add/bind the empty ProductDTO model to a model attritube
        model.addAttribute("product", new ProductDTO());

        //and return us to the add_product.html view for the user
        return "add_product";
    }

    @PostMapping("/add-products")
    public String productForm(@ModelAttribute("product") ProductDTO product, HttpServletRequest httpServletRequest){
        //retrieving the session object (NOTE: Once a session is set it has its own unique ID which once a user's UNIQUE data
        //like ID, username, or email is stored to the session allows the access to that data across the application and the
        //session's id therefor make's  the data only avaliable to user of the unique session.
        //So below try to get the unique session and if the user is not logged in...
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            //...the session will return null and the view to reteun to will be login.html to alert the user they need to login
            return "login";
        }
        Long id = Long.valueOf(session.getAttribute("usernumber").toString());
        //retrieve the user ID that is stored in session and store it in a Long id variable
        //send product object from the add_product.html form data and the user's ID to the productService's saveProduct()
        //method
        productService.saveProduct(product, id);
        //once product is saved please redirect our user to /products/ which is handled in line 36 above
        // to find and add the user's newly saved product object and display it... see line 36 method to understand
        //what operations will happen and what view will finally be served to the user after the redirect:// is called
        //below
        return "redirect:/products/";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id){
        //using the product's id sent via requestParameter or requestParam to find the product by ID and then delete it.
        productService.deleteById(id);
        return "redirect:/products/";
    }
}
