package com.oktrueque.controller;

import com.oktrueque.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Envy on 10/6/2017.
 */
@Controller
public class TruequeController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    private String getUsersItems(@RequestParam(value = "id-user-offerer") Long idUserOfferer, @RequestParam(value = "id-user-demandant") Long idUserDemandant, Model model){

        model.addAttribute("itemsUserOffer", itemService.getItemsByUserId(idUserOfferer));
        model.addAttribute("itemsUserDemand", itemService.getItemsByUserId(idUserDemandant));
        return "trueque";
    }
}
