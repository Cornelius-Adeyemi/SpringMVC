package com.example.springmvc.Controller;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Model.User;
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


    private UserService userService;
    private ProductService productService;

    @Autowired
    public UserController(UserService userService,
                          ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("user") UserDTO userDTO){
        userService.save(userDTO);
        return "login";
    }


    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }
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
        session.removeAttribute("usernumber");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("user", new UserDTO());
        return modelAndView;
    }
}
