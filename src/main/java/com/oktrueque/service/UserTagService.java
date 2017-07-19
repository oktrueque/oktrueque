package com.oktrueque.service;

import com.oktrueque.model.User_Tag;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface UserTagService {

    List<User_Tag> getUserTagByUserId(Long userId);
}
