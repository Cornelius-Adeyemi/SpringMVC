package com.example.springmvc.Model;


import com.example.springmvc.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.BitSet;

@Entity
/*
@Entity annotation defines this class a vital object representing a real life object or an object that exists
in our application's environment, e.g Our application's environment or system is related to storing sold products data
Hence product is an entity of our application. This annotation also notify spring to use JPA via ORM (object relational mapping)
To create a table of the Product class and columns for the product field object in order to store our application's
 object data since our application system demands an object of a Product to be stored. Entity is english means "a thing
 with distinct or independent existence". In order to keep our entity "continuously existent" in our application we have to
persist it, meaning "store it". See? English can help us really understand Java in our own language making it easier to
relate to these foreign concepts.
*/
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_names")
    private String productName;
    private String category;
    private BigDecimal price;

    @ManyToOne
    //creating an entity mapping with the annotation @ManyToOnce which simply means
    //many products can be sold by one user. Therefore, the term "Entity Mapping" or "Entity relationship mapping"
    //means the relationship that objects in our application systems have with each. Product interacts with our user
    //through the action of selling a product and registering the sales. This mapping means the user has the ability to and will
    // eventually interact with more than one product at a time at a some given time while interacting with the product object.
    //So in real life scenarios mapping the relationship of one entity to another just means in layman terms "defining how many times
    //either of objects can interact with each other at a given time.
    @JoinColumn(name = "user_id")
    private User user;

    //Transferring data of our productDTO object to our instantiated new Product() object.
    // or perform ObjectMapping (meaning: "mapping one object's set of data
    // to another object's set of data that possess similar data types" ) with our custom constructor
    public Product(ProductDTO productDTO) {
        this.productName = productDTO.getProductName();
        this.category = productDTO.getCategory();
        this.price =  productDTO.getPrice();
    }

    public Product() {

    }
}
