package com.example.springmvc.Services;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.User;


//Strategy design pattern
//Declaring methods in an interface to be implemented by a class or various class where their implementation will be defined
//based on the class' required functionality.
public interface UserService {

    User save(UserDTO userDTO);
    User findUser(UserDTO userDTO);
}
