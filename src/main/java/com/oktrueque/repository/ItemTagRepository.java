package com.oktrueque.repository;

import com.oktrueque.model.Item_Tag;
import com.oktrueque.model.Item_Tag_Id;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface ItemTagRepository extends CrudRepository<Item_Tag, Item_Tag_Id> {

    List<Item_Tag> findByIdItemId(Long itemId);
}
