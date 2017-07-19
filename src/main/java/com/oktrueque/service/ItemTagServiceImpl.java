package com.oktrueque.service;

import com.oktrueque.model.Item_Tag;
import com.oktrueque.repository.ItemTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public class ItemTagServiceImpl implements ItemTagService {

    private ItemTagRepository itemTagRepository;

    @Autowired
    public ItemTagServiceImpl(ItemTagRepository itemTagRepository){
        this.itemTagRepository = itemTagRepository;
    }

    @Override
    public List<Item_Tag> getItemTagByItemId(Long itemId) {
        return itemTagRepository.findByIdItemId(itemId);
    }
}
