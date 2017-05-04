package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.CategoryService;
import com.oktrueque.service.ItemService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Facundo on 27/04/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;
    private User user = new User();
    @RequestMapping(method = RequestMethod.GET , value="/items")
    public String getItems(Model model){
        List<Item> list = itemService.getItems();
        model.addAttribute("items", list);
        //no creo q sea lo mas eficiente
        model.addAttribute("categories",categoryService.getCategories());
        model.addAttribute("item", new Item());
        user = list.get(0).getUser();
        return "items";
    }

    @RequestMapping(method = RequestMethod.POST , value="/items")
//    BindingResult result, Model model
    public String createItem(@ModelAttribute Item item, BindingResult result) {
        if(result.hasErrors()) {
            return "items";
        }
        item.setUser(user);
        itemService.addItem(item);
        return "redirect:/items";
    }
    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
    public String updateItem(@PathVariable long id, Model model){
        Item itemForUpdate = itemService.getItem(id);
        model.addAttribute("item", itemForUpdate);
        model.addAttribute("items", itemService.getItems());
        model.addAttribute("categories",categoryService.getCategories());
        return "items";
    }

}
