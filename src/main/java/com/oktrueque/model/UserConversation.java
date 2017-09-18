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
    @ManyToOne
    @JoinColumn(name = "id_send_to")
    private User sendTo;

    public UserConversation() {
    }

    public UserConversation(UserConversationId id, LocalDateTime date, User sendTo) {
        this.id = id;
        this.date = date;
        this.sendTo = sendTo;
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

    public User getSendTo() {
        return sendTo;
    }

    public void setSendTo(User sendTo) {
        this.sendTo = sendTo;
    }

    @Override
    public int compareTo(Object o) {
        UserConversation other = (UserConversation) o;
        return this.hashCode() - other.hashCode();
    }
}
