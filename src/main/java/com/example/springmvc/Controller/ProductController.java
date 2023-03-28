package com.example.springmvc.Controller;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ModelAndView homePageWithMV(ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.setViewName("home");
        modelAndView.addObject("product", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/add-products")
    public String productForm(Model model, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            return "login";
        }
        model.addAttribute("product", new ProductDTO());
        return "add_product";
    }
    @PostMapping("/add-products")
    public String productForm(@ModelAttribute("product") ProductDTO product, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            return "login";
        }
        productService.saveProduct(product);
        return "home";
    }
}
