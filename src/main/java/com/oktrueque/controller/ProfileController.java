package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;



@Controller
public class ProfileController {

    private UserService userService;
    private UserTagService userTagService;
    private ItemService itemService;
    private ItemTagService itemTagService;
    private CategoryService categoryService;


    @Autowired
    public ProfileController(UserService userService, UserTagService userTagService, ItemService itemService, ItemTagService itemTagService, CategoryService categoryService) {
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.itemTagService = itemTagService;
        this.categoryService = categoryService;
    }



    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String getProfile(Principal principal, Model model, @PageableDefault(value = 4) Pageable pageable){
        User user = userService.getUserByUsername(principal.getName());
        List<Item> items = itemService.getItemsByUserUsername(user.getUsername(), pageable);
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasScore", user.getScore()!=null? true : false);
        model.addAttribute("hasItems", items.size() != 0 ? true : false);
        model.addAttribute("items", items);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("item", new Item());
        model.addAttribute("categories",categoryService.getCategories());
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit")
    public String editProfile(Principal principal, Model model){
        User user = userService.getUserByUsername(principal.getName());
        List<Tag> tags = userTagService.getTagByUserTags(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit")
    public String updateProfile(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/profile";
    }


    @RequestMapping(method = RequestMethod.GET, value="/profile/items/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        List<ItemTag> tags = itemTagService.getItemTagByItemId(id);
        model.addAttribute("item", item);
        model.addAttribute("user", item.getUser());
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("sugerencias", false);
        return "item";
    }

    @RequestMapping(method = RequestMethod.POST, value="/profile/items")
    public String newItem(@ModelAttribute Item item, Principal principal){
        item.setStatus(0);
        item.setUser(userService.getUserByUsername(principal.getName()));
        itemService.setItem(item);
        return "redirect:/profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/items")
    public String getItemsByUser(Model model, Principal principal){
    List<Item> nonDeletedItems = itemService.getNonDeletedItems(principal.getName());
    model.addAttribute("items", nonDeletedItems);
    return "loggedUserItems";
    }

    @RequestMapping(method = RequestMethod.GET, value="/profile/items/{id}/edit")
    public String fillUpdateView(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        List<ItemTag> tags = itemTagService.getItemTagByItemId(id);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("user", item.getUser());
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("sugerencias", false);
        return "updateItem";
    }

    @RequestMapping(method = RequestMethod.PUT, value="/profile/items/{id}/edit")
    public String updateItemById(@PathVariable Long id, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        Item item = itemService.getItemById(id);
        item.setUser(user);
        item.setStatus(0);
        itemService.updateItem(item);
        return "redirect:/profile/items";
    }

    @RequestMapping(method= RequestMethod.DELETE, value="/profile/items/{id}")
    public String deleteUserItem(@PathVariable Long id){
        Item item = itemService.getItemById(id);
        item.setStatus(2);
        itemService.updateItem(item);
        return "redirect:/profile/items";
    }
}
