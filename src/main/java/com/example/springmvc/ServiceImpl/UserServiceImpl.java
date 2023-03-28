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
        User user = new User(userDTO.getUsername(), userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User findUser(UserDTO userDTO) {
        User user =  userRepository.findByUsername(userDTO.getUsername()).get();
        //TODO:EXPLAIN REASON FOR ERROR WITH HASHING
        boolean match = user.checkpassword(userDTO.getPassword(), user.getPassword());
        if (match){
            return user;
        }
        return null;
    }
}
