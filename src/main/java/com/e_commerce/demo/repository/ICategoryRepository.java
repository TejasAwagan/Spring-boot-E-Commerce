package com.e_commerce.demo.repository;

import com.e_commerce.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String name);

    boolean existsByCategoryName(String name);




}
