package com.oktrueque.controller;


import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTagId;
import com.oktrueque.service.TagServiceImpl;
import com.oktrueque.service.UserServiceImpl;
import com.oktrueque.service.UserTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * Created by Tomas on 26-Jul-17.
 */
@RestController
public class TagController {

    private TagServiceImpl tagServiceImpl;
    private UserServiceImpl userServiceImpl;
    private UserTagServiceImpl userTagServiceImpl;

    @Autowired
    public TagController(TagServiceImpl tagServiceImpl, UserServiceImpl userServiceImpl, UserTagServiceImpl userTagService, UserTagServiceImpl userTagServiceImpl){
        this.tagServiceImpl = tagServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.userTagServiceImpl = userTagServiceImpl;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/tags") //AutoComplete
    public ResponseEntity<List<Tag>> getAllTags(){
        List<Tag> tags = tagServiceImpl.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/edit/tags") //para crear los intereses nuevos
    public ResponseEntity<List<UserTagId>> setUserTagIds(@RequestBody List<UserTagId> tags, Principal principal){

        User user = userServiceImpl.getUserByUsername(principal.getName());

        userTagServiceImpl.saveUserTags(user.getId(), tags);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
