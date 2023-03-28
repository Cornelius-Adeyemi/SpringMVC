package com.example.springmvc.Model;


import com.example.springmvc.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String salt;

    public User(String username, String password) {
        this.username = username;
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, salt);
    }


    public User() {
    }

    public boolean checkpassword(String password, String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }
}
