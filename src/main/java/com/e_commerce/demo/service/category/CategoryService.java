package com.e_commerce.demo.service.category;

import com.e_commerce.demo.exceptions.AlreadyExistsException;
import com.e_commerce.demo.exceptions.ResourceNotFoundException;
import com.e_commerce.demo.models.Category;
import com.e_commerce.demo.repository.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> {
                    throw new ResourceNotFoundException("Category Not found");
                });
    }

    @Override
    public Category getCategoryByName(String name) {
       return Optional.ofNullable(categoryRepository.findByCategoryName(name))
               .orElseThrow(()-> {
                   throw new ResourceNotFoundException("Category Not found");
               });
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByCategoryName(c.getCategoryName()))
                .map(c-> {
                    c.setCategoryId(UUID.randomUUID().toString());
                    return categoryRepository.save(c);
                })
                .orElseThrow(()-> new AlreadyExistsException("Category "+ category.getCategoryName() + " Already Exist"));
    }

    @Override
    public Category updateCategory(Category category, String id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setCategoryName(category.getCategoryName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));

    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
