package com.oktrueque.repository;

import com.oktrueque.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAllByIdIn(List<Long> ids);
}
