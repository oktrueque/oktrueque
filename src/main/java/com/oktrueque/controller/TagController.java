package com.oktrueque.controller;


import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTagId;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * Created by Tomas on 26-Jul-17.
 */
@RestController
public class TagController {

    private TagService tagService;
    private TagServiceImpl tagServiceImpl;
    private UserServiceImpl userServiceImpl;
    private UserTagServiceImpl userTagServiceImpl;
    private UserService userService;
    private UserTagService userTagService;

    @Autowired
    public TagController(TagService tagService, TagServiceImpl tagServiceImpl, UserServiceImpl userServiceImpl,
                         UserTagServiceImpl userTagServiceImpl, UserService userService, UserTagService userTagService) {
        this.tagService = tagService;
        this.tagServiceImpl = tagServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.userTagServiceImpl = userTagServiceImpl;
        this.userService = userService;
        this.userTagService = userTagService;
    }





    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/allTags") //AutoComplete
    public ResponseEntity<List<Tag>> autocomplete(){
        List<Tag> tags = tagServiceImpl.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/edit/tags")
    public ResponseEntity<?> saveUserTags(@RequestBody List<Long> tags){ //tags= lista de longs
        List<Tag> tagsList = tagServiceImpl.findTagsByIds(tags);
        tagServiceImpl.saveTags(tagsList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
