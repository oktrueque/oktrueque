package com.oktrueque.service;

import com.oktrueque.model.Conversation;
import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private final UserConversationRepository userConversationRepository;

    @Autowired
    public ConversationService(UserConversationRepository userConversationRepository) {
        this.userConversationRepository = userConversationRepository;
    }

    public List<Conversation> getAllConversationByUserId(Long userId){
        List<Conversation> conversations = new ArrayList<>();
        List<UserConversation> userConversations = userConversationRepository.findByIdUserId(userId);
        userConversations.forEach(u -> conversations.add(u.getId().getConversation()));
        //Get other Users
        return conversations;
    }
}
