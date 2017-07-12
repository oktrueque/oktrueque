package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.Trueque;
import com.oktrueque.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;


public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

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

    public List<Item> getItemsByUserUsername(String username) {
        return itemRepository.findByUser_Username(username);
    }

    public void updateTruequeItems(List<Item> itemsOffer, List<Item> itemsDemand, Trueque trueque) {
        List<Item> items = new ArrayList<>();
        itemsOffer.forEach(item -> {
            item.setTrueque(trueque);
            item.setStatus(2);
            items.add(item);
        });
        itemsDemand.forEach(item -> {
            item.setTrueque(trueque);
            item.setStatus(2);
            items.add(item);
        });

        itemRepository.save(items);

    }

    public Item setItem(Item item) {
        return itemRepository.save(item);
    }
}
