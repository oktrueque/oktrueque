package com.oktrueque.service;

import com.oktrueque.model.Conversation;
import com.oktrueque.model.Message;
import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.ConversationRepository;
import com.oktrueque.repository.MessageRepository;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserConversationRepository userConversationRepository;
    private final ConversationRepository conversationRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserConversationRepository userConversationRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.userConversationRepository = userConversationRepository;
        this.conversationRepository = conversationRepository;
    }

    public List<Message> getMessagesbyConversationId(Long conversationId){
        return messageRepository.findAllByConversationId(conversationId);
    }

    public Message saveMessage(Message message) {
        saveUserConversation(message);
        return messageRepository.save(message);
    }

    private void saveUserConversation(Message message){
        List<UserConversation> userConversations = userConversationRepository.findByIdConversationId(message.getConversation().getId());
        List<UserConversation> updateUserConversation = new ArrayList<>();
        userConversations.forEach(uc -> {
            if(!Objects.equals(uc.getId().getUser().getId(), message.getUser().getId())){
                uc.setUnread(uc.getUnread()+1);
                updateUserConversation.add(uc);
            }
        });
        userConversationRepository.save(updateUserConversation);
        Conversation conversation = conversationRepository.findOne(message.getConversation().getId());
        conversation.setLastMessage(message.getMessage());
        conversation.setLastMessageDate(message.getDate());
        conversationRepository.save(conversation);
    }
}
