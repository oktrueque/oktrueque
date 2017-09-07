package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.repository.TagRepository;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
    private AwsS3Service awsS3Service;
    @Value("${aws.s3.fileName.users}")
    private String fileNameUsers;
    @Value("${aws.s3.fileName.items}")
    private String fileNameItems;

    @Autowired
    public ProfileController(UserService userService, UserTagService userTagService, ItemService itemService, ItemTagService itemTagService, CategoryService categoryService, TruequeService truequeService, AwsS3Service awsS3Service) {
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.itemTagService = itemTagService;
        this.categoryService = categoryService;
        this.truequeService = truequeService;
        this.awsS3Service = awsS3Service;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String getProfile(Principal principal, Model model, @PageableDefault(value = 5) Pageable pageable){
        User user = userService.getUserByUsername(principal.getName());
        List<Item> items = itemService.findByUser_UsernameAndStatusIsNotOrderById(user.getUsername(),2, pageable);
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        List<UserTrueque> userTrueques= truequeService.getUserTruequeById_UserId(user.getId());
        Trueque TruequeNuevo;
        LinkedList<Trueque> trueques = new LinkedList<>();
        for (UserTrueque trueque: userTrueques){
            TruequeNuevo = truequeService.getTruequeById(trueque.getId().getTruequeId());
            trueques.add(TruequeNuevo);
        }
        model.addAttribute("user", user);
        model.addAttribute("hasScore", user.getScore()!=null? true : false);
        model.addAttribute("hasItems", items.size() != 0 ? true : false);
        model.addAttribute("items", items);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("item", new Item(0));
        model.addAttribute("categories",categoryService.getCategories());
        model.addAttribute("trueques", trueques);
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
    public String updateProfile(@ModelAttribute User user, @ModelAttribute MultipartFile picture) {
        if(!picture.getOriginalFilename().equals("")){
            String pictureUrl = awsS3Service.uploadFileToS3(picture, fileNameUsers, user.getId(), user.getPhoto1());
            user.setPhoto1(pictureUrl);
        }
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
    List<Item> items = itemService.findByUser_UsernameAndStatusIsNotOrderById(principal.getName(),2);
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
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("photo1", item.getPhoto1());
        model.addAttribute("photo2", item.getPhoto2());
        model.addAttribute("photo3", item.getPhoto3());
        return "updateItem";
    }

    @RequestMapping(method = RequestMethod.PUT, value="/profile/items/{id}/edit")
    public String updateItemById(@ModelAttribute Item item, @ModelAttribute List<MultipartFile> pictures, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        item.setUser(user);
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
        List<ItemTrueque> itemsTrueques = truequeService.getItemsTruequeById_TruequeId(id);
        LinkedList<Item> items = new LinkedList<>();
        Item itemNuevo;
        for (ItemTrueque itemTrueque: itemsTrueques){
                itemNuevo = itemService.getItemById(itemTrueque.getId().getItemId());
                items.add(itemNuevo);
        }
        for (UserTrueque userTrueque: userTrueques){
            if(userTrueque.getId().getUserId()!= userLogged.getId()){
                userNuevo = userService.getUserById(userTrueque.getId().getUserId());
                users.add(userNuevo);
            }
        }
        model.addAttribute("items", items);
        model.addAttribute("users", users);
        model.addAttribute("trueque", trueque);
        return "truequeDetail";
    }
}
