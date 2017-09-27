package com.oktrueque.service;

import com.oktrueque.model.Message;

import java.util.List;

/**
 * Created by tomas on 26/9/2017.
 */
public interface MessageService {

    List<Message> getMessagesbyConversationId(Long conversationId);

    Message saveMessage(Message message);
}
