package com.example.springmvc.Controller;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Model.User;
import com.example.springmvc.Repository.UserRepository;
import com.example.springmvc.ServiceImpl.UserServiceImpl;
import com.example.springmvc.Services.ProductService;
import com.example.springmvc.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    //productService bean  and userService bean ( remember, beans are just object of a class but created by Spring boot for you
    //in a Singleton context, meaning once an object is created it will continue to reuse that object to avoid memory waste that
    // may occur from creating multiple object for different uses) will be Injected into the UserController class so they are
    //declared below....

    private UserService userService;
    private ProductService productService;


    //...and they are injected here with the @Autowired annotation which notifies spring to make objects or beans for these
    // classes. If this annotation is omitted you will get a null pointer as userService and productService are not initialized
    // with a new constructor ( i.e. we do not have userService = new UserService())
    // meaning we did not create no objects for both service and also forgot to notify spring to do that also.
    //SO REMEMBER TO AUTOWIRE YOUR CONSTRUCTOR: Meaning once a bean of UserController is created something
    // like similar to this; happeneduserController = new UserController(new UserServiceImpl(), new ProductServiceImpl())
    @Autowired
    public UserController(UserService userService,
                          ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView home(){
        //creating a modelAndView object to bind a Model or Object to in this case UserDTO object
        //and define a view or html page to load for the user while making our userDTO object available to
        //the developer in the view for he/her convenient use.
        ModelAndView modelAndView = new ModelAndView();
        //setting the view or the name of the html file to serve to the user when they make a
        // Get Request to http://localhost:8080/
        modelAndView.setViewName("index");
        //setting an UserDto which we will access in index.html
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/sign-up")
    //Handling a post request made by a user to /sign-up
    //and accessing the data sent from the <form> in index.html with the attribute "user"
    //that we declared in line 57 ("user", new UserDTO())
    //and storing the values in the userDTO in line 66 below
    public String signUp(@ModelAttribute("user") UserDTO userDTO){
        //send our userDTO object to the userService's save() method to save our new user
        //NOTE: Your can do a "CMD + click" on any method to trace its origin
        userService.save(userDTO);
        return "login";
    }


    //Handling user Get requests to /login
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        //setting a view name to serve the user which is the login.html
        modelAndView.setViewName("login");
        //sending a userDTO object by binding it the view with the attribute name "user"
        //which you can find accessed in the login.html file with the Thymeleaf syntax th:object = "${user}"
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    /*
    ProductController class for the comment explaining the code below for
    there is almost 100% similarity between the homePageWithMV() method in
    ProductController class and the loginUser() method below; Starting with the
    fact that they both handle Post http requests made by a user.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest httpServletRequest, Model model){
        User user = userService.findUser(userDTO);
        if (user==null){
            return "login";
        }
        HttpSession session = httpServletRequest.getSession(true);
        Long id = user.getId();
        session.setAttribute("usernumber", id);
        Page<Product> products = productService.findAllByUserId(id);
        PagedListHolder<Product> productsPage = new PagedListHolder<>(products.getContent());
        productsPage.setPage(0);
        productsPage.setPageSize(5);

        model.addAttribute("products", productsPage.getPageList());
        model.addAttribute("currentPage", productsPage.getPage());
        model.addAttribute("totalPages", productsPage.getMaxLinkedPages());
        return "home";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
//        session.removeAttribute("usernumber");
        //to properly remove objects from a session the below method is better
        session.invalidate();
        //because it will remove ALL OBJECTS STORED IN THE SESSION not just the object with attributeName "usernumber"
        //In case you stored more objects in the session you can get rid of all with session.invalidate();
        ModelAndView modelAndView = new ModelAndView();
        //set a html file to serve to the user as the viewName which index.html
        modelAndView.setViewName("index");
        //add a userDTO object to access in index.html file with thymeleaf as explained in line 81 of this Class
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }
}
