package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.ChatService;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.TruequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Envy on 10/6/2017.
 */
@Controller
public class TruequeController {

    private ItemService itemService;
    private TruequeService truequeService;
    private ChatService chatService;

    @Autowired
    public TruequeController(ItemService itemService, TruequeService truequeService, ChatService chatService){
        this.itemService = itemService;
        this.truequeService = truequeService;
        this.chatService = chatService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    public String getUsersItems(@RequestParam(value = "username-user-offerer") String UserOfferer, @RequestParam(value = "username-user-demandant") String UserDemandant, Model model){
        model.addAttribute("itemsUserOffer", itemService.getItemsByUserUsername(UserOfferer));
        model.addAttribute("itemsUserDemand", itemService.getItemsByUserUsername(UserDemandant));
        return "trueque";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques")
    public String registerTrueque(@RequestParam(value = "itemsOffer") ArrayList<Item> itemsOffer,
                                   @RequestParam(value = "itemsDemand") ArrayList<Item> itemsDemand){
        Map<Integer,List<Item>> participants = new LinkedHashMap<>();
        participants.put(1,itemsOffer);
        participants.put(2,itemsDemand);
        truequeService.saveTrueque(participants);
        return "redirect:/profile";
    }




    @RequestMapping(method = RequestMethod.GET, value = "/trueques/{id}/confirm")
    public String confirmTrueque(@PathVariable Long id){
        List<User> users = truequeService.confirmTruequeAndGetUsersBelongingTo(id);
        chatService.createNewsRosters(users,"Trueque"+id);
        return "login";
    }
}
