package com.oktrueque.service;

import com.oktrueque.model.ItemTag;
import com.oktrueque.model.Tag;

import java.util.List;

public interface ItemTagService {

    List<ItemTag> getItemTagByItemId(Long itemId);

    List<Tag> getItemTags(Long itemId);

    void saveItemTags(Long itemId,List<Tag> tags);

    void deleteAllByItemId(Long itemId);

}
