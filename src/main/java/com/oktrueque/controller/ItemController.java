package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTag;
import com.oktrueque.model.User;
import com.oktrueque.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class.getName());

    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;
    private ItemTagService itemTagService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, UserService userService, ItemTagService itemTagService){
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.itemTagService = itemTagService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/items")
    public String getItems(@RequestParam(value = "id_category", required = false) Integer id_category,
                           @RequestParam(value = "item_name", required = false) String item_name,
                           Pageable pageable, Model model) {
        Page<Item> items = null;
        PageWrapper<Item> page = null;
        if (id_category == null && item_name == null) {
            items = itemService.findAll(pageable);
            page = new PageWrapper<>(items, "/items");
        }
        if (id_category != null) {
            try {
                items = itemService.getItemsByCategory(id_category, pageable);
                model.addAttribute("category", categoryService.getCategory(id_category));
                page = new PageWrapper<>(items, "/items?id_category=" + id_category);
            } catch (Exception e) {
                LOGGER.info("Busqueda por categoria incorrecta", e);
            }
        }
        if (item_name != null) {
            try {
                items = itemService.getItemsByName(item_name, pageable);
                model.addAttribute("item_name", item_name);
                page = new PageWrapper<>(items, "/items?item_name=" + item_name);
            } catch (Exception e) {
                LOGGER.info("Busqueda por name incorrecta", e);
            }
        }
        model.addAttribute("items", items.getContent());
        model.addAttribute("page", page);
        model.addAttribute("categories", categoryService.getCategories());
        return "itemsCatalog";
    }

    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
    public String getItemById(@PathVariable Long id, Model model) {

        Item item = itemService.getItemById(id);
        User u = item.getUser();

        List<ItemTag> tags = itemTagService.getItemTagByItemId(id);
        model.addAttribute("item", item);
        model.addAttribute("user", u);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("sugerencias", true);

        return "item";

    }

    @RequestMapping(value = "/itemsList", method = RequestMethod.GET)
    public String list(Model model, Pageable pageable) {
        Page<Item> itemPage = itemService.findAll(pageable);
        PageWrapper<Item> page = new PageWrapper<>(itemPage, "/listItems");
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("page", page);
        return "itemsCatalog";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/items")
    public String createItem(@ModelAttribute Item item) {
        itemService.addItem(item);
        return "redirect:/items";
    }

    @RequestMapping(method= RequestMethod.POST, value="/users/{username}/item")
    public String setItem(@ModelAttribute Item item, @PathVariable String username) {
        item.setStatus(0);
        item.setUser(userService.getUserByUsername(username));
        itemService.setItem(item);
        return "redirect:/users/" + username;
    }

    @RequestMapping(value= "/users/{username}/item", method = RequestMethod.GET)
    public String itemForm(@PathVariable String username, Model model){
        Item item2 = new Item();
        model.addAttribute("item", item2);
        model.addAttribute("user", userService.getUserByUsername(username));
        model.addAttribute("categories", categoryService.getCategories());
        return "createItem";
    }



}
