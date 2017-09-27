package com.oktrueque.service;

import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.List;

public class ConversationServiceImpl implements ConversationService {

    private final UserConversationRepository userConversationRepository;

    @Autowired
    public ConversationServiceImpl(UserConversationRepository userConversationRepository) {
        this.userConversationRepository = userConversationRepository;
    }

    @Override
    public List<UserConversation> getAllConversationByUserId(Long userId){
        return userConversationRepository.findByIdUserId(userId);
    }

    @Override
    @Transactional
    public void clearUnreadMessages(Long conversationId, Long userId) {
        userConversationRepository.clearUnreadMessages(userId, conversationId);
    }
}
