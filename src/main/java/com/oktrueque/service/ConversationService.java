package com.oktrueque.service;

import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final UserConversationRepository userConversationRepository;

    @Autowired
    public ConversationService(UserConversationRepository userConversationRepository) {
        this.userConversationRepository = userConversationRepository;
    }

    public List<UserConversation> getAllConversationByUserId(Long userId){
        return userConversationRepository.findByIdUserId(userId);
    }
}
