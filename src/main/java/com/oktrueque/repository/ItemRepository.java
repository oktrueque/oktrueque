package com.oktrueque.repository;

import com.oktrueque.model.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Facundo on 27/04/2017.
 */
public interface ItemRepository extends CrudRepository<Item, Long> {

}
