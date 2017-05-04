package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addItem(Item item){
        itemRepository.save(item);
    }
    public Item getItem(Long id){
        return itemRepository.findOne(id);
    }


}
