package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


/**
 * Created by Facundo on 8/9/2017.
 */
@Controller
public class ChatController {

    private ChatService chatService;

    @Autowired
    public  ChatController (ChatService chatService){
        this.chatService = chatService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/messages")
    public String getChat(Model model){
        model.addAttribute("usersChatList",chatService.getChatsRegistered());
        return "chat";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login-open-fire")
    public String loginOpenFire(Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        chatService.loginIntoXmppService(user);
        return "redirect:/items";
    }

}