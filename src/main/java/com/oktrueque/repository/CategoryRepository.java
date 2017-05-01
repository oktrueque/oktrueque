package com.oktrueque.repository;

import com.oktrueque.model.Category;
import com.oktrueque.model.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Facundo on 27/04/2017.
 */
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
