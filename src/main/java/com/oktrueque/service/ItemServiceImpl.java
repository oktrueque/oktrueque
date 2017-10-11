package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ItemRepository;
import com.oktrueque.repository.ItemTagRepository;
import com.oktrueque.repository.ItemTruequeRepository;
import com.oktrueque.repository.TagRepository;
import com.oktrueque.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemTagRepository itemTagRepository;
    private final ItemTruequeRepository itemTruequeRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemTagRepository itemTagRepository, ItemTruequeRepository itemTruequeRepository){
        this.itemRepository = itemRepository;
        this.itemTagRepository = itemTagRepository;
        this.itemTruequeRepository = itemTruequeRepository;
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public Item getItemByIdAndStatus(Long id, Integer status) {
        return itemRepository.findByIdAndStatus(id, status);
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
    public Page<Item> getItemsByCategory(int id_category, int status, Pageable pageable) {
     return itemRepository.findByCategory_IdAndStatus(id_category, status, pageable);
    }

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> findByStatus(int status, Pageable pageable) {
        return itemRepository.findByStatus(status,pageable);
    }

    @Override
    public Map<String, Object> searchItems(String name, Principal principal, Pageable pageable) {
        Pattern pattern = Pattern.compile("^(@)(\\w+)");
        Matcher matcher = pattern.matcher(name);
        Map<String, Object> map = new LinkedHashMap<>();
        if (matcher.find()) {
            if((principal != null) && (matcher.group(2).equals(principal.getName()))){
                map.put("items", itemRepository.findByUser_UsernameAndStatusIsNotInOrderByIdDesc(
                        principal.getName(), new int[]{Constants.ITEM_STATUS_DELETED, Constants.ITEM_STATUS_BANNED}, pageable));
                map.put("loggedIn", true);
                return map;
            }
            map.put("items", itemRepository.findByStatusAndUserUsername(Constants.TRUEQUE_STATUS_ACTIVE, matcher.group(2), pageable));
            return map;
        }
        map.put("items", itemRepository.findByNameContainsAndStatus(name, Constants.ITEM_STATUS_ACTIVE, pageable));
        return map;
    }

    @Override
    public List<Item> getItemsByUserUsername(String username, Pageable pageable) {
        return itemRepository.findByUser_Username(username, pageable);
    }

    @Override
    public List<Item> getItemsByUserUsernameAndStatus(String username, Integer status, Pageable pageable) {
        return itemRepository.findByStatusAndUserUsername(status, username, pageable).getContent();
    }

    @Override
    public List<Item> getItemsByUserUsername(String username) {
        return itemRepository.findByUser_Username(username);
    }

    @Override
    public void updateItem(Item item){
        itemRepository.save(item);
    }

    @Override
    public List<Item> getNonDeletedItems(String username){
        return itemRepository.findByUser_UsernameAndStatusIsNotOrderByIdDesc(username,2);
    }

    @Override
    public Item saveItem(Item item) {
        Item itemSaved = itemRepository.save(item);
        List<Tag> tags = item.getTags();
        List<ItemTag> itemTags = new ArrayList<>();
        tags.forEach(t->{
            itemTags.add(new ItemTag(new ItemTagId(itemSaved.getId(),t.getId()),t.getName()));
        });
        itemTagRepository.save(itemTags);
        return itemSaved;
    }

    @Override
    public Page<Item> findByUser_UsernameAndStatusIsNotInOrderById(String username,int[] statuses,Pageable pageable){
        //  2: Eliminado
        //  3: Banneado
        return itemRepository.findByUser_UsernameAndStatusIsNotInOrderByIdDesc(username,statuses,pageable);
    }

    @Override
    public List<Item> findByUser_UsernameAndStatusIsNotOrderById(String username,int status){
        //  2: Eliminado
        return itemRepository.findByUser_UsernameAndStatusIsNotOrderByIdDesc(username,status);
    }

    @Override
    public List<Item> getItemsByUserIdAndStatus(Long id, int status) {
        return itemRepository.findByUserIdAndStatus(id, status);
    }

    @Override
    public Item deleteIfPossible(Long id) {
        List<ItemTrueque> itemTrueques = itemTruequeRepository.findById_ItemId(id);
        if(itemTrueques.size() == 0){
            Item item = itemRepository.findOne(id);
            item.setStatus(Constants.ITEM_STATUS_DELETED);
            itemRepository.save(item);
            return item;
        }
        return null;
    }

    @Override
    public List<Item> getItemsByCategory(Category category, Long id) {
        return itemRepository.findTop4ByCategoryAndIdNot(category, id);
    }
}
