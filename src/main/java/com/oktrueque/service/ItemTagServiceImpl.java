package com.oktrueque.service;

import com.oktrueque.model.ItemTag;
import com.oktrueque.model.ItemTagId;
import com.oktrueque.model.Tag;
import com.oktrueque.repository.ItemTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
@Service
public class ItemTagServiceImpl implements ItemTagService {

    private ItemTagRepository itemTagRepository;

    @Autowired
    public ItemTagServiceImpl(ItemTagRepository itemTagRepository){
        this.itemTagRepository = itemTagRepository;
    }

    @Override
    public List<ItemTag> getItemTagByItemId(Long itemId) {
        return itemTagRepository.findByIdItemId(itemId);
    }

    public void saveItemTags(Long itemId,List<Tag> tags){
        List<ItemTag> itemTagsList = new ArrayList<>();
        for(Tag tag:tags) itemTagsList.add(new ItemTag(new ItemTagId(itemId,tag.getId()),tag.getName()));
        itemTagRepository.save(itemTagsList);
    }
}
