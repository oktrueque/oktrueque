package com.oktrueque.model;

import java.util.Date;

public class MessageLite {

    private String message;
    private Long conversationId;
    private Long userId;
    private Date date;

    public MessageLite(){}

    public MessageLite(String message, Long idConversation, Long userId, Date date) {
        this.message = message;
        this.conversationId = idConversation;
        this.userId = userId;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
