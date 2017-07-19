package com.oktrueque.service;

import com.oktrueque.model.Item_Tag;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface ItemTagService {

    List<Item_Tag> getItemTagByItemId(Long itemId);
}
