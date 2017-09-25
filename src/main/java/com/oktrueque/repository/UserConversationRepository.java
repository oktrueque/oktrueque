package com.oktrueque.repository;

import com.oktrueque.model.UserConversation;
import com.oktrueque.model.UserConversationId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserConversationRepository extends CrudRepository<UserConversation, UserConversationId> {

        List<UserConversation> findByIdUserId(Long idUser);
        List<UserConversation> findByIdConversationId(Long idConversation);
        @Modifying(clearAutomatically = true)
        @Query("UPDATE UserConversation uc SET uc.unread = 0 WHERE uc.id.user.id = :userId AND uc.id.conversation.id = :conversationId")
        int clearUnreadMessages(@Param("userId") Long userId, @Param("conversationId") Long conversationId);
}
