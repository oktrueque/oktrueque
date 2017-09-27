package com.oktrueque.service;

import com.oktrueque.model.UserConversation;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tomas on 26/9/2017.
 */
public interface ConversationService {


    List<UserConversation> getAllConversationByUserId(Long userId);

    @Transactional
    void clearUnreadMessages(Long conversationId, Long userId);

}
