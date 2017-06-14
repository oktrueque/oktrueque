package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 27/04/2017.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems(){
        List<Item> items = new ArrayList<Item>();
        itemRepository.findAll().forEach(items :: add);
        return items;
    }

    public Item getItemById(Long id){ return itemRepository.findOne(id);}

    public void addItem(Item item){
        itemRepository.save(item);
    }

    public void deleteItemAlone(Long id) { itemRepository.delete(id);}

    public List<Item> getItemsByCategory(int id_category) {
        return itemRepository.findByCategory_Id(id_category);
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}
