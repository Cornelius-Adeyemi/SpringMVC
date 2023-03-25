package com.example.springmvc.Controller;

import com.example.springmvc.Model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @GetMapping("/")
    public ModelAndView homePageWithMV(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @GetMapping("/home")
    public String homePage (Model model){
        model.addAttribute("product", new Product());
        return "home";
    }


    @PostMapping("/home")
    public String productForm(@ModelAttribute("product") Product product){
        System.out.println("This is the product: "+product.getProductName());
        return "index";
    }
}
