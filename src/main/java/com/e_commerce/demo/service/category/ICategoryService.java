package com.e_commerce.demo.service.category;

import com.e_commerce.demo.models.Category;
import org.springframework.stereotype.Service;
import java.util.List;


public interface ICategoryService {
    Category getCategoryById(String id);

    Category getCategoryByName(String name);

    Category addCategory(Category category);

    Category updateCategory(Category category, String id);

    void deleteCategoryById (String id);

    List<Category> getAllCategories();
}
