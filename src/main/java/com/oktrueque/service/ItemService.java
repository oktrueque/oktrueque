package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<Item> getItemsByName(String name, Pageable pageable);

    List<Item> getItemsByUserUsername(String username, Pageable pageable);

    List<Item> getItemsByUserUsername(String username);

    Item setItem(Item item);

    Long getMaxUserItemsId(List<Item> list);

    void updateItem(Item item);
}
