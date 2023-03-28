package com.example.springmvc.Repository;

import com.example.springmvc.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
 List<Product> findByUserId(Long id);
}
