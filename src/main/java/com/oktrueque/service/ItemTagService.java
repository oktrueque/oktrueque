package com.oktrueque.service;

import com.oktrueque.model.ItemTag;
import com.oktrueque.model.Tag;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface ItemTagService {

    List<ItemTag> getItemTagByItemId(Long itemId);

    void saveItemTags(Long itemId,List<Tag> tags);

    void deleteAllByItemId(Long itemId);

}
