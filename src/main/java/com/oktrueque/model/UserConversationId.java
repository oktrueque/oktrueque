package com.oktrueque.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserConversationId implements Serializable{

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserLite user;

    public UserConversationId() {
    }

    public UserConversationId(Conversation conversationId, UserLite userId) {
        this.conversation = conversationId;
        this.user = userId;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public UserLite getUser() {
        return user;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConversationId)) return false;
        UserConversationId that = (UserConversationId) o;
        return Objects.equals(conversation.getId(), that.getConversation().getId()) &&
                Objects.equals(user.getId(), that.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversation.getId(), user.getId());
    }
}
