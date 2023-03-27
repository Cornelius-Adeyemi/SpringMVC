package com.example.springmvc.Controller;

import com.example.springmvc.DTO.ProductDTO;
import com.example.springmvc.Model.Product;
import com.example.springmvc.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
//    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView homePageWithMV(ModelAndView modelAndView){
        //ModelAndView is similar RequestDispatcher in servlets
        modelAndView.setViewName("index");
        modelAndView.addObject("product", new ProductDTO());
        modelAndView.addObject("greeting", "Hello");
        return modelAndView;
    }

//    @GetMapping("/home")
//    public String homePage (Model model){

//        model.addAttribute("product", new Product());

//        return "home";
//    }


    @PostMapping("/home")
    public String productForm(@ModelAttribute("product") ProductDTO product){
        productService.saveProduct(product);
        System.out.println("This is the product: "+product.getProductName());
        return "home";
    }
}
