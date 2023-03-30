package com.example.springmvc.Repository;

import com.example.springmvc.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//Please see ProductRepository interface comments for explanations of non-unique properties between both interfaces.
public interface UserRepository extends JpaRepository<User, Long> {

//Using the below unique method to find the User by their username
    //this returns and Optional which means: Instead of failing your sql query if no data is available you have
    //the "Option" of getting a null value instead.
    // PUN VERY MUCH INTENDED
    Optional<User> findByUsername(String username);


}
