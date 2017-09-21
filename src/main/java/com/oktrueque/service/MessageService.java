package com.oktrueque.service;

import com.oktrueque.model.Message;
import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.MessageRepository;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserConversationRepository userConversationRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserConversationRepository userConversationRepository) {
        this.messageRepository = messageRepository;
        this.userConversationRepository = userConversationRepository;
    }

    public List<Message> getMessagesbyConversationId(Long conversationId){
        return messageRepository.findAllByConversationId(conversationId);
    }

    public Message saveMessage(Message response) {
        return messageRepository.save(response);
    }

    public void saveUserConversation(Message message){
        UserConversation userConversation = new UserConversation();


    }
}
