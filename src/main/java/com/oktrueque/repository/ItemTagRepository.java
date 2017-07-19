package com.oktrueque.repository;

import com.oktrueque.model.ItemTag;
import com.oktrueque.model.ItemTagId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface ItemTagRepository extends CrudRepository<ItemTag, ItemTagId> {

    List<ItemTag> findByIdItemId(Long itemId);
}
