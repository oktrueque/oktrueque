package com.oktrueque.service;

import com.oktrueque.model.Tag;
import com.oktrueque.model.UserTag;
import com.oktrueque.repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
@Service
public class UserTagServiceImpl implements UserTagService{

    private UserTagRepository userTagRepository;
    private TagService tagService;

    @Autowired
    public UserTagServiceImpl(UserTagRepository userTagRepository, TagService tagService){
        this.userTagRepository = userTagRepository;
        this.tagService = tagService;
    }

    @Override
    public List<UserTag> getUserTagByUserId(Long userId) {
        return userTagRepository.findByIdUserId(userId);
    }

    public List<Tag> getTagByUserTags(Long userId){
        List<UserTag> userTags = userTagRepository.findByIdUserId(userId);
        List<Long> tagsId = new ArrayList<>();
        userTags.forEach(userTag -> tagsId.add(userTag.getId().getTagId()));
        return tagService.findTagsByIds(tagsId);
    }

//    public void saveUserTags(Long userId,List<UserTagId> tags){
//
//        for(UserTagId tag:tags) {
//
//
//            userTagRepository.setByUserId(userId, tag.getTagId());
//
//        }
//
//
//    }


}
