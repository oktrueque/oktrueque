package com.oktrueque.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTruequeId implements Serializable{

    @ManyToOne
    @JoinColumn(name = "id_trueque")
    private Trueque trueque;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserLite user;

    public UserTruequeId() {
    }

    public UserTruequeId(Trueque trueque, UserLite user) {
        this.trueque = trueque;
        this.user = user;
    }

    public Trueque getTrueque() {
        return trueque;
    }

    public void setTrueque(Trueque trueque) {
        this.trueque = trueque;
    }

    public UserLite getUser() {
        return user;
    }

    public void setUser(UserLite user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTruequeId)) return false;
        UserTruequeId that = (UserTruequeId) o;
        return Objects.equals(trueque.getId(), that.getTrueque().getId()) &&
                Objects.equals(user.getId(), that.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(trueque.getId(), user.getId());
    }



}
