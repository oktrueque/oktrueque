package com.oktrueque.controller;

import com.oktrueque.model.Category;
import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.CategoryService;
import com.oktrueque.service.ItemService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @RequestMapping(method = RequestMethod.GET , value="/items")
    @Valid
    public String getItems(@RequestParam(value = "id_category", required = false) Integer id_category, @RequestParam(value = "item_name", required = false) String item_name, Model model){
        List<Item> items = null;
        if(id_category == null && item_name == null) items = itemService.getItems();
        if(id_category != null)
        {
            try{
                items = itemService.getItemsByCategory(id_category);
                model.addAttribute("category", categoryService.getCategory(id_category));
            }
            catch(Exception e){}
        }
        if(item_name != null){
            try{
                items = itemService.getItemsByName(item_name);
                model.addAttribute("item_name", item_name);
            }
            catch(Exception e){}
        }
        model.addAttribute("items", items);
        model.addAttribute("categories",categoryService.getCategories());
        return "itemsCatalog";
    }
    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
    public String getItemById(@PathVariable Long id, Model model){
        model.addAttribute("item" , itemService.getItemById(id));
        return "itemView";
    }

    @RequestMapping(method = RequestMethod.POST , value="/items")
    public String createItem(@ModelAttribute Item item) {
        itemService.addItem(item);
        return "redirect:/items";
    }


}
