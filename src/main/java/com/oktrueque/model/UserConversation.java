package com.oktrueque.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_conversation")
public class UserConversation implements Comparable {

    @EmbeddedId
    private UserConversationId id;

    @Column(name = "date")
    private LocalDateTime date;

    public UserConversation() {
    }

    public UserConversation(UserConversationId id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public UserConversationId getId() {
        return id;
    }

    public void setId(UserConversationId id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        UserConversation other = (UserConversation) o;
        return this.hashCode() - other.hashCode();
    }
}
