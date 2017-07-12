package com.oktrueque.service;

import com.oktrueque.model.Category;

import java.util.List;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface CategoryService  {

    List<Category> getCategories();

    Category getCategory(Integer id);
}
