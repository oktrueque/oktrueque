package com.oktrueque.repository;

import com.oktrueque.model.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, Long>{
}
