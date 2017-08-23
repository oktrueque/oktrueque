package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Facundo on 8/23/2017.
 */

@Embeddable
public class UserTruequeId implements Serializable{

    @Column(name = "id_trueque")
    private Long truequeId;
    @Column(name = "id_user")
    private Long userId;

    public UserTruequeId() {
    }

    public UserTruequeId(Long truequeId, Long userId) {
        this.truequeId = truequeId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTruequeId() {
        return truequeId;
    }

    public void setTruequeId(Long truequeId) {
        this.truequeId = truequeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTruequeId)) return false;
        UserTruequeId that = (UserTruequeId) o;
        return Objects.equals(truequeId, that.getTruequeId()) &&
                Objects.equals(userId, that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(truequeId,userId);
    }



}
