package com.oktrueque.service;

import com.oktrueque.model.User_Tag;
import com.oktrueque.repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public class UserTagServiceImpl implements UserTagService{

    private UserTagRepository userTagRepository;

    @Autowired
    public UserTagServiceImpl(UserTagRepository userTagRepository){
        this.userTagRepository = userTagRepository;
    }

    @Override
    public List<User_Tag> getUserTagByUserId(Long userId) {
        return userTagRepository.findByIdUserId(userId);
    }
}
