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
//    @Autowired
//    private CategoryService categoryService;
    private User user = new User();
    private List<Item> items = null;

    @RequestMapping(method = RequestMethod.GET , value="/items")
    @Valid
    public String getItems(@RequestParam(value = "id_category", required = false) Integer id_category, Model model){
        if(id_category == null) items = itemService.getItems();
        else{
            try{
                items = itemService.getItemsByCategory(id_category);
            }
            catch(Exception e){}
        }
        model.addAttribute("items", items);
//        model.addAttribute("categories",categoryService.getCategories()); //Esto debería ser reemplazado, sirve para probar nada mas.
//        model.addAttribute("item", new Item());
//        user = items.get(0).getUser();
        return "itemsCatalog";
    }
    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
    public String getItemById(@PathVariable Long id, Model model){
        model.addAttribute("item" , itemService.getItemById(id));
        return "itemView";
    }

    @RequestMapping(method = RequestMethod.POST , value="/items")
    public String createItem(@ModelAttribute Item item, BindingResult result) {
        if(result.hasErrors()) {
            return "items";
        }
        item.setUser(user);
        itemService.addItem(item);
        return "redirect:/items";
    }

//    @RequestMapping(method = RequestMethod.GET, value="/items/{id}")
//    public String updateItem(@PathVariable Long id, Model model){
//        if (items == null){
//            items = itemService.getItems();
//        }
//        //Expresion Lambda para buscar el item q queremos editar
//        Item itemForUpdate =  items.stream()
//                .filter(item -> item.getId() == id)
//                .findFirst()
//                .orElse(null);
//        model.addAttribute("item" , itemForUpdate);
//        model.addAttribute("items", items);
//        model.addAttribute("categories",categoryService.getCategories());
//        return "itemView";
//    }

//    @RequestMapping(method = RequestMethod.DELETE , value="/items/{id}")
//    public String deleteItems(@PathVariable long id){
//        itemService.deleteItemAlone(id);
//        return "redirect:/items";
//    }

}
