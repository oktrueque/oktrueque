package com.oktrueque.repository;

import com.oktrueque.model.Category;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Integer> {

    int findCategoryByName(String name);
}
