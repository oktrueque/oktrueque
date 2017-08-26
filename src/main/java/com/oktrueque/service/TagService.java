package com.oktrueque.service;

import com.oktrueque.model.Tag;

import java.util.List;

/**
 * Created by nicolas on 19/07/17.
 */
public interface TagService {
    List<Tag> findTagsByIds(List<Long> tagsId);

    List<Tag> findAll();

    void saveTags(List<Tag> tags);
}
