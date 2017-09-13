package com.oktrueque.repository;

import com.oktrueque.model.UserConversation;
import com.oktrueque.model.UserConversationId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserConversationRepository extends CrudRepository<UserConversation, UserConversationId> {

        List<UserConversation> findByIdUserId(Long idUser);
}
