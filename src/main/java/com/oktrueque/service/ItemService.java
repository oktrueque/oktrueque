package com.oktrueque.service;

import com.oktrueque.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface ItemService {

    Item getItemById(Long id);

    void addItem(Item item);

    void deleteItem(Long id);

    Page<Item> getItemsByCategory(int id_category, Pageable pageable);

    Page<Item> findAll(Pageable pageable);

    Page<Item> findByStatus(int status, Pageable pageable);

    Page<Item> getItemsByName(String name, Pageable pageable);

    List<Item> getItemsByUserUsername(String username, Pageable pageable);

    List<Item> getItemsByUserUsername(String username);

    void updateItem(Item item);

    List<Item> getNonDeletedItems(String username);

    Item saveItem(Item item);

    List<Item> findByUser_UsernameAndStatusIsNotInOrderById(String username,int[] statuses,Pageable pageable);

    List<Item> findByUser_UsernameAndStatusIsNotOrderById(String username,int status);

    List<Item> getItemsByUserId(Long id);
}
