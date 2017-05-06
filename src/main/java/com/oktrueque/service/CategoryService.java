package com.oktrueque.service;

import com.oktrueque.model.Category;
import com.oktrueque.model.Item;
import com.oktrueque.repository.CategoryRepository;
import com.oktrueque.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 27/04/2017.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<Category>();
        categoryRepository.findAll().forEach(categories :: add);
        return categories;
    }


}
