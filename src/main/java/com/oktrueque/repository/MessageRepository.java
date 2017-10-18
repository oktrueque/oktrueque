package com.oktrueque.repository;

import com.oktrueque.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByConversationId(Long conversationId);
    void deleteAllByConversationId(Long conversationId);
}
