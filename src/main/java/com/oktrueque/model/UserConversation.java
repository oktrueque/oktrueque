package com.oktrueque.model;

import javax.persistence.*;

@Entity
@Table(name = "user_conversation")
public class UserConversation implements Comparable {

    @EmbeddedId
    private UserConversationId id;
    @ManyToOne
    @JoinColumn(name = "id_send_to")
    private UserLite sendTo;
    @Column(name = "is_group")
    private Boolean group;
    @Column(name = "unread")
    private Integer unread;

    public UserConversation() {
    }

    public UserConversation(UserConversationId id, UserLite sendTo) {
        this.id = id;
        this.sendTo = sendTo;
    }

    public UserConversation(UserConversationId id, Boolean group) {
        this.id = id;
        this.group = group;
        this.unread = 1;
    }

    public UserConversationId getId() {
        return id;
    }

    public void setId(UserConversationId id) {
        this.id = id;
    }

    public UserLite getSendTo() {
        return sendTo;
    }

    public void setSendTo(UserLite sendTo) {
        this.sendTo = sendTo;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    @Override
    public int compareTo(Object o) {
        UserConversation other = (UserConversation) o;
        return this.hashCode() - other.hashCode();
    }
}
