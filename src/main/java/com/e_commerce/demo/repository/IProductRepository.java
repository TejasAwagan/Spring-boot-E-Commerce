package com.e_commerce.demo.repository;

import com.e_commerce.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByBrand(String brand);

    List<Product> findByProductName(String name);

    boolean existsByProductName(String productName);


}
