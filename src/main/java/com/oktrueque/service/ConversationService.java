package com.oktrueque.service;

import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.model.UserConversation;
import com.oktrueque.model.UserTrueque;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface ConversationService {


    List<UserConversation> getAllConversationByUserId(Long userId);

    @Transactional
    void clearUnreadMessages(Long conversationId, Long userId);

    Map<String, Object> getUnreadMessages(User user);

    void createConversation(Trueque trueque, List<UserTrueque> userTrueques);

    void deleteConversation(Trueque trueque, List<UserTrueque> userTrueques);
}
