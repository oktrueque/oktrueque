package com.oktrueque.controller;

import com.oktrueque.model.ComplaintType;
import com.oktrueque.model.Item;
import com.oktrueque.model.ItemTag;
import com.oktrueque.model.UserLite;
import com.oktrueque.service.*;
import com.oktrueque.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class.getName());

    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;
    private ItemTagService itemTagService;
    private ComplaintService complaintService;
    private ComplaintTypeService complaintTypeService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, UserService userService, ItemTagService itemTagService, ComplaintService complaintService, ComplaintTypeService complaintTypeService){
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.itemTagService = itemTagService;
        this.complaintService = complaintService;
        this.complaintTypeService = complaintTypeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/items")
    public String getItems(@RequestParam(value = "id_category", required = false) Integer id_category,
                           @RequestParam(value = "search", required = false) String search,
                           Pageable pageable, Model model, Principal principal) {
        Page<Item> items = null;
        PageWrapper<Item> page = null;
        model.addAttribute("loggedIn", false);
        if (id_category == null && search == null) {
            items = itemService.findByStatus(1,pageable);
            page = new PageWrapper<>(items, "/items");
        }
        if (id_category != null) {
            try {
                items = itemService.getItemsByCategory(id_category,Constants.ITEM_STATUS_ACTIVE, pageable);
                model.addAttribute("category", categoryService.getCategory(id_category));
                page = new PageWrapper<>(items, "/items?id_category=" + id_category);
            } catch (Exception e) {
                LOGGER.info("Busqueda por categoria incorrecta", e);
            }
        }
        if (search != null) {
            try {
                Map<String, Object> map = itemService.searchItems(search, principal, pageable);
                items = (Page<Item>) map.get("items");
                if(map.containsKey("loggedIn")) model.addAttribute("loggedIn", map.get("loggedIn"));
                page = new PageWrapper<>(items, "/items?search=" + search);
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

        Item item = itemService.getItemByIdAndStatus(id, Constants.ITEM_STATUS_ACTIVE);
        UserLite u = item.getUser();
        List<ComplaintType> complaintTypes = complaintTypeService.getComplaintTypes();
        List<ItemTag> tags = itemTagService.getItemTagByItemId(id);
        model.addAttribute("complaintTypes", complaintTypes);
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

    @RequestMapping(value= "/users/{username}/item", method = RequestMethod.GET)
    public String itemForm(@PathVariable String username, Model model){
        Item item2 = new Item();
        model.addAttribute("item", item2);
        model.addAttribute("user", userService.getUserByUsername(username));
        model.addAttribute("categories", categoryService.getCategories());
        return "createItem";
    }



}
