package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users_trueques")
public class UserTrueque implements Comparable {

    @EmbeddedId
    private UserTruequeId id;
    @Column(name = "orden")
    private Integer order;
    @Column(name= "status")
    private Integer status;
    @Column(name="show_actions")
    private Boolean showActions;


    public UserTrueque() {
    }

    public UserTruequeId getId() {
        return id;
    }

    public UserTrueque(UserTruequeId id, Integer order, Integer status, Boolean showActions) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.showActions = showActions;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getShowActions() {
        return showActions;
    }

    public void setShowActions(Boolean showActions) {
        this.showActions = showActions;
    }

    @Override
    public int compareTo(Object o) {
        UserTrueque other = (UserTrueque) o;
        return this.hashCode() - other.hashCode();
    }
}
