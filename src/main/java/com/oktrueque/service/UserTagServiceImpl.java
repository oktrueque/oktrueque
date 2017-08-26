package com.oktrueque.service;

import com.oktrueque.model.Tag;
import com.oktrueque.model.UserTag;
import com.oktrueque.model.UserTagId;
import com.oktrueque.repository.UserTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public class UserTagServiceImpl implements UserTagService{

    private UserTagRepository userTagRepository;
    private TagService tagService;

    public UserTagServiceImpl(UserTagRepository userTagRepository, TagService tagService){
        this.userTagRepository = userTagRepository;
        this.tagService = tagService;
    }

    @Override
    public List<UserTag> getUserTagByUserId(Long userId) {
        return userTagRepository.findByIdUserId(userId);
    }

    @Override
    public List<Tag> getTagByUserTags(Long userId){
        List<UserTag> userTags = userTagRepository.findByIdUserId(userId);
        List<Long> tagsId = new ArrayList<>();
        userTags.forEach(userTag -> tagsId.add(userTag.getId().getTagId()));
        return tagService.findTagsByIds(tagsId);
    }

    @Override
    public void saveUserTags(Long userId,List<Tag> tags){
        List<UserTag> userTagsList = new ArrayList<>();
        for(Tag tag:tags) userTagsList.add(new UserTag(new UserTagId(userId,tag.getId()),tag.getName()));
        userTagRepository.save(userTagsList);
    }

    @Override
    @Transactional
    public void deleteAllByUserId(Long userId){ userTagRepository.removeAllById_UserId(userId);    }


}
