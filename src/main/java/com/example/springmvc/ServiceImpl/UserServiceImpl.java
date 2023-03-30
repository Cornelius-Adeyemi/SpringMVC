package com.example.springmvc.ServiceImpl;

import com.example.springmvc.DTO.UserDTO;
import com.example.springmvc.Model.User;
import com.example.springmvc.Repository.UserRepository;
import com.example.springmvc.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDTO userDTO) {
        User existingUser = userRepository.findByUsername(userDTO.getUsername()).get();
        if (existingUser!=null){
            throw new RuntimeException("User already exists with username: "+ userDTO.getUsername());
        }
        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User findUser(UserDTO userDTO) {
        User user =  userRepository.findByUsername(userDTO.getUsername()).get();
        //Using the checkPassword method in our user model class we can verify user's password credentials
        //and either grant or reject their Authority (in application security terms) or their "right"(in layman's terms)
        // to access our application's secure endpoint.
        boolean match = user.checkPassword(userDTO.getPassword(), user.getPassword());
        if (match){
            return user;
        }
        return null;
    }
}
