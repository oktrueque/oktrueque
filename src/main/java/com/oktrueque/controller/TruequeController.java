package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.TruequeService;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Envy on 10/6/2017.
 */
@Controller
public class TruequeController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TruequeService truequeService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    private String getUsersItems(@RequestParam(value = "id-user-offerer") Long idUserOfferer, @RequestParam(value = "id-user-demandant") Long idUserDemandant, Model model){
        model.addAttribute("itemsUserOffer", itemService.getItemsByUserId(idUserOfferer));
        model.addAttribute("itemsUserDemand", itemService.getItemsByUserId(idUserDemandant));
        return "trueque";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques")
    private String registerTrueque(@RequestParam(value = "itemsOffer") ArrayList<Item> itemsOffer, @RequestParam(value = "itemsDemand") ArrayList<Item> itemsDemand){
        truequeService.saveTrueque(itemsOffer, itemsDemand);
        return "redirect:/users/" + itemsOffer.get(0).getUser().getId();
    }

}
