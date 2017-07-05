package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.CategoryService;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ItemController {

    private static final Logger LOGGER = Logger.getLogger(ItemController.class.getName());

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

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
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
        if (item_name != null) {
            try {
                items = itemService.getItemsByName(item_name, pageable);
                model.addAttribute("item_name", item_name);
                page = new PageWrapper<>(items, "/items?item_name=" + item_name);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
        model.addAttribute("items", items.getContent());
        model.addAttribute("page", page);
        model.addAttribute("categories", categoryService.getCategories());
        return "itemsCatalog";
    }

    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));

        //Borrar la siguiente linea cuando tengamos el Iniciar Sesion y el id del user se guarde en local storage
        model.addAttribute("user_id", 351);
        return "itemView";
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
