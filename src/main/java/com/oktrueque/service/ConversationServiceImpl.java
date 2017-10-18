package com.oktrueque.service;

import com.oktrueque.model.*;
import com.oktrueque.repository.ConversationRepository;
import com.oktrueque.repository.UserConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.*;

public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserConversationRepository userConversationRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository, UserConversationRepository userConversationRepository) {
        this.conversationRepository = conversationRepository;
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

    @Override
    @Transactional
    public void createConversation(Trueque trueque, List<UserTrueque> userTrueques) {
        Conversation conversation = conversationRepository.save(new Conversation(new Date(), trueque.getId(), "Comienza una nueva conversación"));
        Boolean isGroup = userTrueques.size()>2;
        List<UserConversation> userConversations = new ArrayList<>();
        for (int i = 0; i < userTrueques.size(); i++) {
            UserTrueque ut = userTrueques.get(i);
            UserConversation userConversation = new UserConversation(new UserConversationId(conversation, ut.getId().getUser()), isGroup);
            if(!isGroup){
                if(i == 0) userConversation.setSendTo(userTrueques.get(1).getId().getUser());
                if(i == 1) userConversation.setSendTo(userTrueques.get(0).getId().getUser());
            }
            userConversations.add(userConversation);
        }
        userConversationRepository.save(userConversations);
    }
}
