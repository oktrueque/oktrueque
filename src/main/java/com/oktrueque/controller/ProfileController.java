package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

/**
 * Created by nicolas on 21/07/17.
 */
@Controller
public class ProfileController {

    private UserService userService;
    private UserTagService userTagService;
    private ItemService itemService;

    @Autowired
    public ProfileController(UserServiceImpl userService,  UserTagService userTagService, ItemServiceImpl itemService){
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String getProfile(Principal principal, Model model, @PageableDefault(value = 4) Pageable pageable){
        User user = userService.getUserByUsername(principal.getName());
        List<Item> items = itemService.getItemsByUserUsername(user.getUsername(), pageable);
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasItems", items.size() != 0 ? true : false);
        model.addAttribute("items", items);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit")
    public String editProfile(Principal principal, Model model){
        User user = userService.getUserByUsername(principal.getName());
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit")
    public String updateUser(@ModelAttribute User user) {
        user.setStatus(0);
        userService.updateUser(user);
        return "redirect:/profile";
    }

}
