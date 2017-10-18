package com.oktrueque.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "id_trueque")
    private Long idTrueque;
    @Column(name = "last_message_text")
    private String lastMessage;
    @Column(name = "last_message_time")
    private Date lastMessageDate;

    public Conversation() {
    }

    public Conversation(Long id){
        this.id = id;
    }

    public Conversation(Date date, Long idTrueque) {
        this.date = date;
        this.idTrueque = idTrueque;
    }

    public Conversation(Date date, Long idTrueque, String lastMessage) {
        this.date = date;
        this.idTrueque = idTrueque;
        this.lastMessage = lastMessage;
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

    public Long getIdTrueque() {
        return idTrueque;
    }

    public void setIdTrueque(Long idTrueque) {
        this.idTrueque = idTrueque;
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
}
