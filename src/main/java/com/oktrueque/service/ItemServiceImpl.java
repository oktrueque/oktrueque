package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }

    @Override
    public Page<Item> getItemsByCategory(int id_category, Pageable pageable) {
        return itemRepository.findByCategory_Id(id_category, pageable);
    }

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> getItemsByName(String name, Pageable pageable) {
        return itemRepository.findByNameContains(name, pageable);
    }

    @Override
    public List<Item> getItemsByUserUsername(String username, Pageable pageable) {
        return itemRepository.findByUser_Username(username, pageable);
    }

    @Override
    public List<Item> getItemsByUserUsername(String username) {
        return itemRepository.findByUser_Username(username);
    }

    @Override
    public Item setItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Long getMaxUserItemsId(List<Item> list){

        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item item2, Item item1)
            {
                return  item1.getId().compareTo(item2.getId());
            }
        });

        return list.get(0).getId();
    }

    @Override
    public void updateItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> getNonDeletedItems(String username){
        return itemRepository.findByUser_UsernameAndStatusIsNot(username,2);
    }
}
