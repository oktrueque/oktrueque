package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.TruequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * Created by Envy on 10/6/2017.
 */
@Controller
public class TruequeController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TruequeService truequeService;

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    private String getUsersItems(@RequestParam(value = "username-user-offerer") String UserOfferer, @RequestParam(value = "username-user-demandant") String UserDemandant, Model model){
        model.addAttribute("itemsUserOffer", itemService.getItemsByUserUsername(UserOfferer));
        model.addAttribute("itemsUserDemand", itemService.getItemsByUserUsername(UserDemandant));
        return "trueque";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques")
    private String registerTrueque(@RequestParam(value = "itemsOffer") ArrayList<Item> itemsOffer, @RequestParam(value = "itemsDemand") ArrayList<Item> itemsDemand){
        truequeService.saveTrueque(itemsOffer, itemsDemand);
        return "redirect:/users/" + itemsOffer.get(0).getUser().getUsername();
    }

}
