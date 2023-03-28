package com.example.springmvc.Services;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.User;

public interface UserService {

    User save(UserDTO userDTO);
    User findUser(UserDTO userDTO);
}
