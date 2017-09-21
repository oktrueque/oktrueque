package com.oktrueque.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_conversation")
public class UserConversation implements Comparable {

    @EmbeddedId
    private UserConversationId id;
    @ManyToOne
    @JoinColumn(name = "id_send_to")
    private User sendTo;
    @Column(name = "is_group")
    private Boolean group;

    public UserConversation() {
    }

    public UserConversation(UserConversationId id, Date date, User sendTo) {
        this.id = id;
        this.sendTo = sendTo;
    }

    public UserConversationId getId() {
        return id;
    }

    public void setId(UserConversationId id) {
        this.id = id;
    }

    public User getSendTo() {
        return sendTo;
    }

    public void setSendTo(User sendTo) {
        this.sendTo = sendTo;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    @Override
    public int compareTo(Object o) {
        UserConversation other = (UserConversation) o;
        return this.hashCode() - other.hashCode();
    }
}
