package com.oktrueque.controller;

import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.TruequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TruequeController {

    private ItemService itemService;
    private TruequeService truequeService;

    @Autowired
    public TruequeController(ItemService itemService, TruequeService truequeService){
        this.itemService = itemService;
        this.truequeService = truequeService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/trueques/{id}/accept")
    public String confirmTrueque(@PathVariable Long id){
        List<User> users = truequeService.confirmTruequeAndGetUsersBelongingTo(id);
        return "redirect:/profile/trueques/" + id;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques/{idTrueque}")
    public ResponseEntity editTrueque(@PathVariable Long idTrueque, @RequestBody List<Long> ids){
        System.out.println(ids);
        return new ResponseEntity(HttpStatus.OK);
    }
}
