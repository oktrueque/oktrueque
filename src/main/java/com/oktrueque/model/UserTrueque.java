package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Facundo on 8/23/2017.
 */
@Entity
@Table(name = "users_trueques")
public class UserTrueque implements Comparable {

    @EmbeddedId
    private UserTruequeId id;

    @Column(name = "orden")
    private Integer order;

    @Column(name= "is_confirm")
    private Boolean isConfirm;

    public UserTrueque() {
    }

    public UserTruequeId getId() {
        return id;
    }

    public UserTrueque(UserTruequeId id,Integer orden, boolean isConfirm) {
        this.id = id;
        this.order = orden;
        this.isConfirm = isConfirm;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    @Override
    public int compareTo(Object o) {
        UserTrueque other = (UserTrueque) o;
        return this.hashCode() - other.hashCode();
    }
}
