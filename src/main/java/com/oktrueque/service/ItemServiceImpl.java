package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTag;
import com.oktrueque.model.ItemTagId;
import com.oktrueque.model.Tag;
import com.oktrueque.repository.ItemRepository;
import com.oktrueque.repository.ItemTagRepository;
import com.oktrueque.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemTagRepository itemTagRepository;
    private final TagRepository tagRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemTagRepository itemTagRepository, TagRepository tagRepository){
        this.itemRepository = itemRepository;
        this.itemTagRepository = itemTagRepository;
        this.tagRepository = tagRepository;
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
    public void updateItem(Item item){


        itemRepository.save(item);
    }

    public List<Item> getNonDeletedItems(String username){
        return itemRepository.findByUser_UsernameAndStatusIsNotOrderByIdDesc(username,2);
    }

    @Override
    @Transactional
    public void saveItem(Item item) {
//        Item itemSaved = itemRepository.save(item);
//        List<Tag> tags = tagRepository.findAllByIdIn(item.getIdTags());
//        List<ItemTag> itemTags = new ArrayList<>();
//        tags.forEach(t->{
//            itemTags.add(new ItemTag(new ItemTagId(itemSaved.getId(),t.getId()),t.getName()));
//        });
//        itemTagRepository.save(itemTags);
    }

    public List<Item> findByUser_UsernameAndStatusIsNotOrderById(String username,int status,Pageable pageable){
        //  2: Eliminado
        return itemRepository.findByUser_UsernameAndStatusIsNotOrderByIdDesc(username,status,pageable);
    }

    public List<Item> findByUser_UsernameAndStatusIsNotOrderById(String username,int status){
        //  2: Eliminado
        return itemRepository.findByUser_UsernameAndStatusIsNotOrderByIdDesc(username,status);
    }


}
