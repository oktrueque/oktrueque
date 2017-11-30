package com.oktrueque.repository;

import com.oktrueque.model.Category;
import com.oktrueque.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findByCategory_IdAndStatus(int id, int status, Pageable pageable);

    Item findByIdAndStatus(long id, int status);

    Page<Item> findByNameContainsAndStatus(String name, int status, Pageable pageable);

    List<Item> findByUser_Username(String username);

    List<Item> findByUser_Username(String username, Pageable pageable);

    List<Item> findByUser_UsernameAndStatusIsNotOrderByIdDesc(String username, int status);

    List<Item> findByUser_UsernameAndStatusIsNotInOrderByIdDesc(String username, int[] statuses);

    Page<Item> findByUser_UsernameAndStatusIsNotInOrderByIdDesc(String username, int[] statuses, Pageable pageable);

    Page<Item> findByStatusAndUserUsername(Integer status, String username, Pageable pageable);

    List<Item> findByStatusAndUserUsername(Integer status, String username);

    Page<Item> findByStatus(int status, Pageable pageable);

    List<Item> findByUserIdAndStatus(Long id, Integer status);

    List<Item> findTop4ByCategoryAndIdNotAndStatus(Category category, Long id,Integer status);

    Page<Item> findByStatusOrderByCreationDateDesc(int status, Pageable pageable);
}
