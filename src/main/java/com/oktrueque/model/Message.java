package com.oktrueque.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Message() {
    }

    public Message(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    public Message(Date date, Conversation conversation, String message, User user) {
        this.date = date;
        this.conversation = conversation;
        this.message = message;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
