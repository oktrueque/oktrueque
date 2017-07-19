package com.oktrueque.service;

import com.oktrueque.model.UserTag;
import com.oktrueque.repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
@Service
public class UserTagServiceImpl implements UserTagService{

    private UserTagRepository userTagRepository;

    @Autowired
    public UserTagServiceImpl(UserTagRepository userTagRepository){
        this.userTagRepository = userTagRepository;
    }

    @Override
    public List<UserTag> getUserTagByUserId(Long userId) {
        return userTagRepository.findByIdUserId(userId);
    }
}
