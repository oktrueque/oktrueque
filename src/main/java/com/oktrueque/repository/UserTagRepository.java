package com.oktrueque.repository;

import com.oktrueque.model.User_Tag;
import com.oktrueque.model.User_Tag_Id;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface UserTagRepository extends CrudRepository<User_Tag, User_Tag_Id> {

    List<User_Tag> findByIdUserId(Long userId);
}
