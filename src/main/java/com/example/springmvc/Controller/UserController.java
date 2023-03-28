package com.example.springmvc.Controller;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.User;
import com.example.springmvc.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        User user = userService.save(userDTO);
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
    public String loginUser(@ModelAttribute("user") UserDTO userDTO, HttpServletRequest httpServletRequest){
        User user = userService.findUser(userDTO);
        if (user==null){
            return "login";
        }
        HttpSession session = httpServletRequest.getSession();
        Long id = user.getId();
        session.setAttribute("usernumber", id);
        return "home";
    }
}
