package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.ConversationService;
import com.oktrueque.service.MessageService;
import com.oktrueque.service.UserService;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    private final ConversationService conversationService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    @Autowired
    public ChatController(ConversationService conversationService, MessageService messageService, SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/{id}/messages")
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long id){
        List<Message> messages = messageService.getMessagesbyConversationId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/{id}/clear-unread")
    public ResponseEntity<Long> clearUnreadMessages(@PathVariable Long id, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        conversationService.clearUnreadMessages(id, user.getId());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @MessageMapping("/messages/{username}")
    public void send(@Payload MessageLite message, @DestinationVariable("username") String username) throws Exception {
        User user = new User();
        user.setId(message.getUserId());
        Message response = new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
        Message saved = messageService.saveMessage(response);
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply", saved);
    }

    @MessageMapping("/messages/room/{id}")
    public void sendToGroup(@Payload MessageLite message, @DestinationVariable("id") Long id) throws Exception {
        User user = userService.getUserById(message.getUserId());
        Message response = new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
        messageService.saveMessage(response);
        simpMessagingTemplate.convertAndSend("/topic/" + message.getConversationId(), response);
    }

}
