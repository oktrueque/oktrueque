package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.repository.TagRepository;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;



@Controller
public class ProfileController {

    private UserService userService;
    private UserTagService userTagService;
    private ItemService itemService;
    private ItemTagService itemTagService;
    private CategoryService categoryService;
    private TruequeService truequeService;


    @Autowired
    public ProfileController(UserService userService, UserTagService userTagService, ItemService itemService, ItemTagService itemTagService, CategoryService categoryService, TruequeService truequeService) {
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.itemTagService = itemTagService;
        this.categoryService = categoryService;
        this.truequeService = truequeService;
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
        user.setStatus(0);
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
    List<Item> items = itemService.getItemsByUserUsername(principal.getName());
    model.addAttribute("items", items);
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
    public String updateItemById(@ModelAttribute Item item, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        item.setUser(user);
        item.setStatus(0);
        itemService.updateItem(item);
        return "redirect:/profile/items";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/trueques")
    public String getTruequesByUser(Model model, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        List<UserTrueque> userTrueques= truequeService.getUserTruequeById_UserId(user.getId());

        Trueque TruequeNuevo;
        LinkedList<Trueque> trueques = new LinkedList<>();

        for (UserTrueque trueque: userTrueques){

            TruequeNuevo = truequeService.getTruequeById(trueque.getId().getTruequeId());
            trueques.add(TruequeNuevo);
        }
        model.addAttribute("trueques", trueques);

        return "loggedUserTrueques";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/profile/trueques/{id}")
    public String getTruequeById(Model model,@PathVariable Long id,Principal principal){
        User userLogged = userService.getUserByUsername(principal.getName());
        Trueque trueque = truequeService.getTruequeById(id);
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_TruequeId(id);
        LinkedList<User> users = new LinkedList<>();
        User userNuevo;
        List<ItemTrueque> items;

        for (UserTrueque userTrueque: userTrueques){

            if(userTrueque.getId().getUserId()!= userLogged.getId()){
                userNuevo = userService.getUserById(userTrueque.getId().getUserId());
                users.add(userNuevo);
            }
        }


        model.addAttribute("users", users);
        model.addAttribute("trueque", trueque);
        return "truequeDetail";
    }


}
