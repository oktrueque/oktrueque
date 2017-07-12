package com.oktrueque.service;

import com.oktrueque.model.Category;
import com.oktrueque.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;


public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Category getCategory(Integer id) {
        return categoryRepository.findOne(id);
    }

}
