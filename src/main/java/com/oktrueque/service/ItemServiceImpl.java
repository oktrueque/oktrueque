package com.oktrueque.service;

import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTag;
import com.oktrueque.model.ItemTagId;
import com.oktrueque.model.Tag;
import com.oktrueque.repository.ItemRepository;
import com.oktrueque.repository.ItemTagRepository;
import com.oktrueque.repository.TagRepository;
import com.oktrueque.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public Page<Item> findByStatus(int status, Pageable pageable) {
        return itemRepository.findByStatus(status,pageable);
    }

    @Override
    public Page<Item> searchItems(String name, String principal, Pageable pageable) {
        Pattern pattern = Pattern.compile("^(:)(\\w+)");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            if(matcher.group(2).equals(principal)){
                return itemRepository.findByUser_UsernameAndStatusIsNotInOrderByIdDesc(
                        principal, new int[]{Constants.ITEM_STATUS_DELETED, Constants.ITEM_STATUS_BANNED}, pageable);
            }
            return itemRepository.findByStatusAndUserUsername(Constants.TRUEQUE_STATUS_ACTIVE, matcher.group(2), pageable);
        }
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
    public List<Item> findByUser_UsernameAndStatusIsNotInOrderById(String username,int[] statuses,Pageable pageable){
        //  2: Eliminado
        //  3: Banneado
        return itemRepository.findByUser_UsernameAndStatusIsNotInOrderByIdDesc(username,statuses,pageable).getContent();
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
}
