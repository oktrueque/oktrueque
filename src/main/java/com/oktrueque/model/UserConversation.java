package com.oktrueque.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
    @Column(name = "last_message_text")
    private String lastMessage;
    @Column(name = "last_message_time")
    private Date lastMessageDate;


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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    @Override
    public int compareTo(Object o) {
        UserConversation other = (UserConversation) o;
        return this.hashCode() - other.hashCode();
    }
}
