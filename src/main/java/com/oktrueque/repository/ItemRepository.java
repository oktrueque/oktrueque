package com.oktrueque.repository;

import com.oktrueque.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findByCategory_Id(int id, Pageable pageable);

    Page<Item> findByName(String name, Pageable pageable);
}
