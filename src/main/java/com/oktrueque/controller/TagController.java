package com.oktrueque.controller;


import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTag;
import com.oktrueque.service.TagServiceImpl;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * Created by Tomas on 26-Jul-17.
 */
@RestController
public class TagController {

    private TagServiceImpl tagServiceImpl;
    private UserService userService;

    @Autowired
    public TagController(TagServiceImpl tagServiceImpl, UserService userService){
        this.tagServiceImpl = tagServiceImpl;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/tags") //AutoComplete
    public ResponseEntity<List<Tag>> getAllTags(){
        List<Tag> tags = tagServiceImpl.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
//    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/tags")
//    public @ResponseBody List<Tag> getTags(){
//        return tagServiceImpl.findAll();
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/edit/tags") //para crear los intereses nuevos
    public String setUserTags(Principal principal, @RequestBody UserTag userTag){
        User user = userService.getUserByUsername(principal.getName());

        return "updateProfile";
    }


}
