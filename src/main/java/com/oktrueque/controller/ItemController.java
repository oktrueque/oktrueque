package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.service.CategoryService;
import com.oktrueque.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Facundo on 27/04/2017.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET , value="/items")
    public String getItems(Model model){
        model.addAttribute("items", itemService.getItems());
        //no creo q sea lo mas eficiente
        model.addAttribute("categories",categoryService.getCategories());
        model.addAttribute("item", new Item());
        return "items";
    }

    @RequestMapping(method = RequestMethod.POST , value="/items")
    public String createItem(@ModelAttribute Item item, Model model) {
        model.addAttribute("item",item);
        itemService.addItem(item);
        return "redirect:/items";
    }
}
