package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }

    public Item getItemById(Long id) {
        return itemRepository.findOne(id);
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }

    public Page<Item> getItemsByCategory(int id_category, Pageable pageable) {
        return itemRepository.findByCategory_Id(id_category, pageable);
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> getItemsByName(String name, Pageable pageable) {
        return itemRepository.findByName(name, pageable);
    }
}
