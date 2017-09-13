package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserConversationId implements Serializable{

    @Column(name = "id_conversation")
    private Long conversationId;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public UserConversationId() {
    }

    public UserConversationId(Long conversationId, User userId) {
        this.conversationId = conversationId;
        this.user = userId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConversationId)) return false;
        UserConversationId that = (UserConversationId) o;
        return Objects.equals(conversationId, that.getConversationId()) &&
                Objects.equals(user.getId(), that.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, user.getId());
    }
}
