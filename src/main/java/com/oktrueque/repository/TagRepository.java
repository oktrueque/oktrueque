package com.oktrueque.repository;

import com.oktrueque.model.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicolas on 19/07/17.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
}
