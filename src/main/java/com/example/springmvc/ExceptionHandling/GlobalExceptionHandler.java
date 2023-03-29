package com.example.springmvc.ExceptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView handleException(Exception e){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
