package com.oktrueque.controller;


import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTag;
import com.oktrueque.model.UserTagId;
import com.oktrueque.service.*;
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
    private UserService userService;
    private UserTagService userTagService;

    @Autowired
    public TagController(TagServiceImpl tagServiceImpl, UserServiceImpl userServiceImpl, UserTagServiceImpl userTagServiceImpl, UserService userService, UserTagService userTagService) {
        this.tagServiceImpl = tagServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.userTagServiceImpl = userTagServiceImpl;
        this.userService = userService;
        this.userTagService = userTagService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/allTags") //AutoComplete
    public ResponseEntity<List<Tag>> autocomplete(Principal principal){
        List<Tag> tags = tagServiceImpl.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit") //AutoComplete
//    public ResponseEntity<List<Tag>> getAllTags(Principal principal){
//        User user = userService.getUserByUsername(principal.getName());
//        List<UserTag> tagsList = userTagService.findAll();
//        List<UserTag> userTags = userTagServiceImpl.getUserTagByUserId(user.getId());
//        for (UserTag userTag: tagsList){
//            if(tagsList.listIterator().next().equals(userTag)){
//                tagsList.remove(userTag);
//            }
//        }
//        return new ResponseEntity<>(tagsList, HttpStatus.OK);
//    }



    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit/tags") //para crear los intereses nuevos
    public ResponseEntity<?> setUserTagIds(@RequestBody List<UserTagId> tags, Principal principal){

        User user = userServiceImpl.getUserByUsername(principal.getName());

//        userTagServiceImpl.saveUserTags(user.getId(), tags);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
