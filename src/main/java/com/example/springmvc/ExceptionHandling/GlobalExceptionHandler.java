package com.example.springmvc.ExceptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
/*
@ControllerAdvice registers the class as the controller exception handling class
 */
public class GlobalExceptionHandler {

    //@ExceptionHandler defines the Exception our handleException() method below is supposed to handle
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView handleException(Exception e){
        //upon coming across an exception serve the error.html view for the user
        ModelAndView modelAndView = new ModelAndView("error");

        //bind the message String object of the exception to an attributeName "errorMessage"
        //to be retrieved in the error.html file for display to the user
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
