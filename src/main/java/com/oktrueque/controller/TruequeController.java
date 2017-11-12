package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.TruequeService;
import com.oktrueque.service.UserService;
import com.oktrueque.service.UserTagService;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TruequeController {

    private final ItemService itemService;
    private final TruequeService truequeService;
    private final UserService userService;
    private final UserTagService userTagService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public TruequeController(ItemService itemService, TruequeService truequeService, UserService userService, UserTagService userTagService, SimpMessagingTemplate simpMessagingTemplate){
        this.itemService = itemService;
        this.truequeService = truequeService;
        this.userService = userService;
        this.userTagService = userTagService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    public String getUsersItems(@RequestParam(value = "username-user-offerer") String UserOfferer, @RequestParam(value = "username-user-demandant") String UserDemandant, Model model){
        User user = userService.getUserByUsername(UserOfferer);
        List<Tag> tags = userTagService.getTagByUserTags(user.getId());
        model.addAttribute("itemsUserOffer", itemService.getItemsByUserUsernameAndStatus(UserOfferer, Constants.ITEM_STATUS_ACTIVE));
        model.addAttribute("itemsUserDemand", itemService.getItemsByUserUsernameAndStatus(UserDemandant, Constants.ITEM_STATUS_ACTIVE));
        model.addAttribute("offerer", user);
        model.addAttribute("demandant", userService.getUserByUsername(UserDemandant));
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "trueque";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques")
    public String registerTrueque(@RequestParam(value = "itemsOffer") ArrayList<Item> itemsOffer,
                                   @RequestParam(value = "itemsDemand") ArrayList<Item> itemsDemand,
                                  Principal principal){
        Map<Integer,List<Item>> participants = new LinkedHashMap<>();
        participants.put(1,itemsOffer);
        participants.put(2,itemsDemand);
        truequeService.saveTrueque(participants, principal.getName());
        return "redirect:/profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trueques/{id}/user/{username}/accept")
    public String confirmTrueque(@PathVariable Long id, @PathVariable String username, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        //truequeService.acceptTruequeAndGetUsersBelongingTo(id, username);
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_TruequeId(id);
        List<String> usernamesToSendNotification = new ArrayList<>();
        for(UserTrueque ut : userTrueques){
            if(!ut.getId().getUser().getUsername().equals(user.getUsername())){
                usernamesToSendNotification.add(ut.getId().getUser().getUsername());
            }
        }
        sendNotificationToUsers(usernamesToSendNotification, user.getName());
        return "redirect:/profile/trueques/" + id;
    }

    private void sendNotificationToUsers(List<String> usernames, String name) {
        for(String username : usernames){
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply", new Message(
                    String.format("%s, %s ha aceptado el trueque propuesto, se ha abierto un chat en la pestaña " +
                            "Mensajes para que puedan ponerse de acuerdo para realizar el trueque", username, name)
            ));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trueques/{idTrueque}")
    @Transactional
    public ResponseEntity<String> editTrueque(@PathVariable Long idTrueque, @RequestBody List<Long> ids, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        String message = truequeService.updateTrueque(idTrueque, ids, user.getId());
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
