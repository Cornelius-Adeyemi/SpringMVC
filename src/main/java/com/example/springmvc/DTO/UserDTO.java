package com.example.springmvc.DTO;

import lombok.Data;

@Data
/*
See ProductDTO Class for the explanation of the purpose of a DTO (Data Transfer Object)
 */
public class UserDTO {
    private String username;
    private String password;
}
