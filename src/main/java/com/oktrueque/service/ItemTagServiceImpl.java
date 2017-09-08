package com.oktrueque.service;

import com.oktrueque.model.ItemTag;
import com.oktrueque.model.ItemTagId;
import com.oktrueque.model.Tag;
import com.oktrueque.repository.ItemTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */

public class ItemTagServiceImpl implements ItemTagService {

    private final ItemTagRepository itemTagRepository;

    public ItemTagServiceImpl(ItemTagRepository itemTagRepository){
        this.itemTagRepository = itemTagRepository;
    }

    @Override
    public List<ItemTag> getItemTagByItemId(Long itemId) {
        return itemTagRepository.findByIdItemId(itemId);
    }

    @Override
    public List<Tag> getItemTags(Long itemId) {
        List<ItemTag> itemTags =  itemTagRepository.findByIdItemId(itemId);
        List<Tag> tags = new ArrayList<>();
        itemTags.forEach(itemTag -> tags.add(new Tag(itemTag.getId().getTagId(), itemTag.getName())));
        return tags;
    }

    @Override
    public void saveItemTags(Long itemId,List<Tag> tags){
        List<ItemTag> itemTagsList = new ArrayList<>();
        for(Tag tag:tags) itemTagsList.add(new ItemTag(new ItemTagId(itemId,tag.getId()),tag.getName()));
        itemTagRepository.save(itemTagsList);
    }

    @Override
    @Transactional
    public void deleteAllByItemId(Long itemId){ itemTagRepository.removeAllById_ItemId(itemId);    }

}

