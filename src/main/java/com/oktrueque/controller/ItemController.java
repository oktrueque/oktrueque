package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.service.CategoryService;
import com.oktrueque.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Facundo on 27/04/2017.
 */
@Controller
public class ItemController {

    private static final Logger LOGGER = Logger.getLogger(ItemController.class.getName());

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET , value="/items")
    public String getItems(@RequestParam(value = "id_category", required = false) Integer id_category,
                            @RequestParam(value = "item_name", required = false) String item_name,
                            Pageable pageable, Model model){
        Page<Item> items = null ;
        PageWrapper<Item> page = null;
        if(id_category == null && item_name == null){
            items= itemService.findAll(pageable);
            page = new PageWrapper<Item>(items, "/items");
        }
        if(id_category != null)
        {
            try{
                items = itemService.getItemsByCategory(id_category,pageable);
                model.addAttribute("category", categoryService.getCategory(id_category));
                page = new PageWrapper<Item>(items, "/items?id_category=" +id_category);
            }
            catch(Exception e){
            }
        }
        if(item_name != null){
            try{
                items = itemService.getItemsByName(item_name,pageable);
                model.addAttribute("item_name", item_name);
                page = new PageWrapper<Item>(items, "/items?item_name=" +item_name);
            }
            catch(Exception e){}
        }
        model.addAttribute("items", items.getContent());
        model.addAttribute("page", page);
        model.addAttribute("categories",categoryService.getCategories());
        return "itemsCatalog";
    }
    @RequestMapping(value = "/itemsList", method = RequestMethod.GET)
    public String list(Model model,Pageable pageable){
        Page<Item> itemPage = itemService.findAll(pageable);
        PageWrapper<Item> page = new PageWrapper<Item>(itemPage, "/listItems");
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("page", page);
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
