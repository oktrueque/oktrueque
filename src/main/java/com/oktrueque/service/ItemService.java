package com.oktrueque.service;

import com.oktrueque.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ItemService {

    Item getItemById(Long id);

    Item getItemByIdAndStatus(Long id, Integer status);

    void addItem(Item item);

    void deleteItem(Long id);

    Page<Item> getItemsByCategory(int id_category, int status, Pageable pageable);

    Page<Item> findAll(Pageable pageable);

    Page<Item> findByStatus(int status, Pageable pageable);

    Map<String, Object> searchItems(String name, Principal principal, Pageable pageable);

    List<Item> getItemsByUserUsername(String username, Pageable pageable);

    List<Item> getItemsByUserUsername(String username);

    List<Item> getItemsByUserUsernameAndStatus(String username, Integer status);

    List<Item> getItemsByUserUsernameAndStatus(String username, Integer status, Pageable pageable);

    void updateItem(Item item);

    List<Item> getNonDeletedItems(String username);

    Item saveItem(Item item);

    Page<Item> findByUser_UsernameAndStatusIsNotInOrderById(String username,int[] statuses,Pageable pageable);

    List<Item> findByUser_UsernameAndStatusIsNotOrderById(String username,int status);

    List<Item> getItemsByUserIdAndStatus(Long id, int status);

    Item deleteIfPossible(Long id);

    List<Item> getItemsByCategory(Category category, Long id);
}
