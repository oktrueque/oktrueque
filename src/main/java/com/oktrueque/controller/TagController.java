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
    public ResponseEntity<Void> saveUserTags(@RequestBody List<Long> tagsId, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<Tag> tagsList = tagService.findTagsByIds(tagsId);
        userTagService.deleteAllByUserId(user.getId()); //Los elimino antes, para que solo queden guardados los que estan en el input.
        userTagService.saveUserTags(user.getId(),tagsList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/items/{id}/updateItemTags")
    public ResponseEntity<Void> updateItemTags(@RequestBody List<Tag> tags, @PathVariable Long id){
        Item item = itemService.getItemById(id);
        itemTagService.deleteAllByItemId(item.getId());
        itemTagService.saveItemTags(item.getId(),tags);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
