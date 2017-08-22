package com.oktrueque.controller;


import com.oktrueque.model.Item;
import com.oktrueque.model.Tag;
import com.oktrueque.model.User;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
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
    private ItemTagServiceImpl itemTagServiceImpl;
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    public TagController(TagService tagService, TagServiceImpl tagServiceImpl, UserServiceImpl userServiceImpl,
                         UserTagServiceImpl userTagServiceImpl, UserService userService, UserTagService userTagService, ItemTagServiceImpl itemTagServiceImpl, ItemServiceImpl itemServiceImpl) {
        this.tagService = tagService;
        this.tagServiceImpl = tagServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.userTagServiceImpl = userTagServiceImpl;
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemTagServiceImpl = itemTagServiceImpl;
        this.itemServiceImpl = itemServiceImpl;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tags") //AutoComplete
    public ResponseEntity<List<Tag>> autocomplete(){
        List<Tag> tags = tagServiceImpl.findAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/userTags")
    public ResponseEntity<Void> saveUserTags(@RequestBody List<Long> tagsId, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<Tag> tagsList = tagServiceImpl.findTagsByIds(tagsId);
        userTagServiceImpl.deleteAllByUserId(user.getId()); //Los elimino antes, para que solo queden guardados los que estan en el input.
        userTagServiceImpl.saveUserTags(user.getId(),tagsList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/itemTags")
    public ResponseEntity<Void> saveItemTags(@RequestBody List<Long> tagsId, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<Tag> tagsList = tagServiceImpl.findTagsByIds(tagsId);
        List<Item> userItems = itemServiceImpl.getItemsByUserUsername(user.getUsername());
        Long maxUserItemId = itemServiceImpl.getMaxUserItemsId(userItems);
        itemTagServiceImpl.saveItemTags(maxUserItemId,tagsList); //NO SIRVE CUANDO HACES UPDATE SOLO CUANDO CREAS
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
