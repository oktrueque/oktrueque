package com.oktrueque.repository;

import com.oktrueque.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findByCategory_Id(int id, Pageable pageable);
    Page<Item> findByNameContains(String name, Pageable pageable);
    List<Item> findByUser_Username(String username);
    List<Item> findByUser_Username(String username, Pageable pageable);
    List<Item> findByUser_UsernameAndStatusIsNotOrderByIdDesc(String username, int status);
    List<Item> findByUser_UsernameAndStatusIsNotInOrderByIdDesc(String username, int[] statuses, Pageable pageable);


}
