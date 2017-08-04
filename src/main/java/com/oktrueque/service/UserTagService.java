package com.oktrueque.service;

import com.oktrueque.model.UserTag;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface UserTagService {

    List<UserTag> getUserTagByUserId(Long userId);

}
