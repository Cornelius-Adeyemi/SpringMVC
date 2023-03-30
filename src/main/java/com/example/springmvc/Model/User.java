package com.example.springmvc.Model;



import jakarta.persistence.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;


@Entity
/*
see Product model for Entity annotation's explanation.
 */
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    //Salt: A randomly generated data added to an encrypted data to be used as the key for decrypting information
    private String salt;

    public User(String username, String password) {
        this.username = username;

        //Using the dependency org.mindrot.jbcrypt.BCrypt's gensalt() method to generating a salt to add to our hash password
        //below
        this.salt = BCrypt.gensalt();
        //using the hashpw() to hash or encrypt our password and adding our salt to it.
        this.password = BCrypt.hashpw(password, salt);
    }


    public User() {
    }

    //Using the checkPassword() method to verify the user's password inputted in the same as hashed password stored in the DB
    public boolean checkPassword(String password, String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }
}
