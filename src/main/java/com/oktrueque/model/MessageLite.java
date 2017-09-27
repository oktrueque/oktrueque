package com.oktrueque.model;

import java.util.Date;

public class MessageLite {

    private String message;
    private Long conversationId;
    private Long userId;
    private Date date;
    private String userPhoto;

    public MessageLite(){}

    public MessageLite(String message, Long idConversation, Long userId, Date date, String userPhoto) {
        this.message = message;
        this.conversationId = idConversation;
        this.userId = userId;
        this.date = date;
        this.userPhoto = userPhoto;
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

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
