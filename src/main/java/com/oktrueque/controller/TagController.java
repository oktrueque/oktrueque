package com.oktrueque.controller;


import com.oktrueque.model.Item;
import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


/**
 * Created by Tomas on 26-Jul-17.
 */
@RestController
public class TagController {

    private TagService tagService;
    private UserService userService;
    private UserTagService userTagService;
    private ItemService itemService;
    private ItemTagService itemTagService;

    @Autowired
    public TagController(TagService tagService, UserService userService, UserTagService userTagService,
                         ItemTagService itemTagService, ItemService itemService) {
        this.tagService = tagService;
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.itemTagService = itemTagService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tags") //AutoComplete
    public ResponseEntity<List<Tag>> autocomplete(){
        List<Tag> tags = tagService.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/userTags")
    @Transactional
    public ResponseEntity<Void> saveUserTags(@RequestBody List<Tag> tags, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        userTagService.deleteAllByUserId(user.getId());
        userTagService.saveUserTags(user.getId(),tags);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/items/{id}/updateItemTags")
    @Transactional
    public ResponseEntity<Void> updateItemTags(@RequestBody List<Tag> tags, @PathVariable Long id){
        Item item = itemService.getItemById(id);
        itemTagService.deleteAllByItemId(item.getId());
        itemTagService.saveItemTags(item.getId(),tags);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
