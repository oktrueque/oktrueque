package com.oktrueque.repository;

import com.oktrueque.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Facundo on 27/04/2017.
 */
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    List<Item> findByCategory_Id(int id);
}
