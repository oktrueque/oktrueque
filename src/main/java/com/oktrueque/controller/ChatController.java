package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class ChatController {

    private final ConversationService conversationService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final TruequeService truequeService;

    @Autowired
    public ChatController(ConversationService conversationService, MessageService messageService,
                          SimpMessagingTemplate simpMessagingTemplate, UserService userService, TruequeService truequeService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.truequeService = truequeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations")
    public String getConversations(Model model, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<UserConversation> conversations = conversationService.getAllConversationByUserId(user.getId());
        List<Long> groups = new ArrayList<>();
        conversations.forEach(c -> {
            if(c.getGroup()) groups.add(c.getId().getConversation().getId());
        });
        model.addAttribute("conversations", conversations);
        model.addAttribute("groups", groups);
        model.addAttribute("user", user);
        return "chat";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/notifications")
    public ResponseEntity<Map<String, Object>> getUnreadConversations(Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Map map = conversationService.getUnreadMessages(user);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/{id}/messages")
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long id){
        List<Message> messages = messageService.getMessagesbyConversationId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/{id}/trueque")
    public ResponseEntity<Map<String, Object>> clearUnreadMessages(@PathVariable Long id, @RequestParam Long truequeId, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        conversationService.clearUnreadMessages(id, user.getId());
        Map map = truequeService.getTruequeDetail(truequeId);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @MessageMapping("/messages/{username}")
    public void send(@Payload MessageLite message, @DestinationVariable("username") String username) throws Exception {
        UserLite user = userService.getUserLiteById(message.getUserId());
        Message response = new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
        Message saved = messageService.saveMessage(response);
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply", saved);
    }

    @MessageMapping("/messages/room/{id}")
    public void sendToGroup(@Payload MessageLite message, @DestinationVariable("id") Long id) throws Exception {
        UserLite user = userService.getUserLiteById(message.getUserId());
        Message response = new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
        messageService.saveMessage(response);
        simpMessagingTemplate.convertAndSend("/topic/" + message.getConversationId(), response);
    }

}
