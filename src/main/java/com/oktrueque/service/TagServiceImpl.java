package com.oktrueque.service;

import com.oktrueque.model.Tag;
import com.oktrueque.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll(){
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public List<Tag> findTagsByIds(List<Long> tagsId) {
        return tagRepository.findAllByIdIn(tagsId);
    }

    public void saveTags(List<Tag> tags){
//        for (Tag tag:tags) {
//            tagRepository.save(tag);
//        }
        tagRepository.save(tags);

    }
}
