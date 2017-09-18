package com.oktrueque.controller;

import com.oktrueque.model.Conversation;
import com.oktrueque.model.Message;
import com.oktrueque.model.MessageLite;
import com.oktrueque.model.User;
import com.oktrueque.service.ConversationService;
import com.oktrueque.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    private final ConversationService conversationService;
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(ConversationService conversationService, MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations")
    public String getConversations(Model model, Principal principal){
        User user =(User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("conversations", conversationService.getAllConversationByUserId(user.getId()));
        model.addAttribute("user", user);
        return "chat";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/conversations/{id}/messages")
    public ResponseEntity<List<Message>> getConversationMessages(@PathVariable Long id){
        List<Message> messages = messageService.getMessagesbyConversationId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public Message send(@Payload MessageLite message) throws Exception {
        User user = new User();
        user.setId(message.getUserId());
        return new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
    }

    @MessageMapping("/messages/{username}")
    public void send2(@Payload MessageLite message, @DestinationVariable("username") String username, Principal principal) throws Exception {
        User user = new User();
        user.setId(message.getUserId());
        Message response = new Message(new Date(), new Conversation(message.getConversationId()), message.getMessage(), user);
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/reply", response);
    }

}
