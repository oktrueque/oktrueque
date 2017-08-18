package com.oktrueque.repository;

import com.oktrueque.model.UserTag;
import com.oktrueque.model.UserTagId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface UserTagRepository extends CrudRepository<UserTag, UserTagId> {

    List<UserTag> findByIdUserId(Long userId);
    void removeAllById_UserId(Long userId);

}
