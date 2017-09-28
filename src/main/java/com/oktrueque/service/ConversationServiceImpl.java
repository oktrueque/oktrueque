package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.model.UserConversation;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getUnreadMessages(User user) {
        List<UserConversation> conversations = userConversationRepository.findByIdUserId(user.getId());
        Integer count = 0;
        List<Long> groups = new ArrayList<>();
        for(UserConversation c : conversations){
            if(c.getGroup()) groups.add(c.getId().getConversation().getId());
            count += c.getUnread();
        }
        Map map = new LinkedHashMap();
        map.put("unread", count);
        map.put("groups", groups);
        return map;
    }
}
