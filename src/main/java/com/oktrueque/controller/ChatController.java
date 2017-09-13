package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ChatController {

    private final ConversationService conversationService;

    @Autowired
    public ChatController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/messages")
    public String getConversations(Model model, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("conversations", conversationService.getAllConversationByUserId(user.getId()));
        return "chat";
    }

}
