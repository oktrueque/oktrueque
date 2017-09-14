package com.oktrueque.service;

import com.oktrueque.model.Message;
import com.oktrueque.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesbyConversationId(Long conversationId){
        return messageRepository.findAllByConversationId(conversationId);
    }
}
