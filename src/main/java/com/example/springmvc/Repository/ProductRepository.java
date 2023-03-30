package com.example.springmvc.Repository;

import com.example.springmvc.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Repository interface simply notify spring to handle this interface as
//the point of communication between our application and our database management system or in this case our PostgreSQL service.
public interface ProductRepository extends JpaRepository<Product, Long> {

 //creating a custom method by taking advantage of JPA's ability to link our Java objects to our DB table columns.
 //We create a method that searches for all the products available in the database belonging a user with a certain user ID.
 //This method is related to the annotation @ManyToOne in our product model. As it is this method uses the relationship between
 //product and user Entity to query the database's table relationship between products and users table
 List<Product> findByUserId(Long id);
}
