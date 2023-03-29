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


    private ProductService productService;
    private ProductRepository productRepository;


    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/products/")
    public ModelAndView homePageWithMV(@RequestParam(defaultValue = "0") int page,  ModelAndView modelAndView, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("usernumber")==null){
            modelAndView.setViewName("login");
            return modelAndView;
        }
        Page<Product> products = productService.findAllByUserId((Long)session.getAttribute("usernumber"));
        PagedListHolder<Product> productsPage = new PagedListHolder<>(products.getContent());
        productsPage.setPage(page);
        productsPage.setPageSize(5);
        modelAndView.setViewName("home");
        modelAndView.addObject("products", productsPage.getPageList());
        modelAndView.addObject("currentPage", productsPage.getPage());
        modelAndView.addObject("totalPages", productsPage.getMaxLinkedPages());
        return modelAndView;
    }

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
        log.info("Product id: --->"+productDTO.getId());
        Product product = productService.findProduct(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
        return "redirect:/products/";

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
        Long id = Long.valueOf(session.getAttribute("usernumber").toString());
        productService.saveProduct(product, id);
        return "redirect:/products/";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id){
        productService.deleteById(id);
        return "redirect:/products/";
    }
}
