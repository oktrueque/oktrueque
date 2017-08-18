package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTagId implements Serializable{

    @Column(name = "id_user")
    private Long userId;
    @Column(name = "id_tag")
    private Long tagId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tagId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTagId)) return false;
        UserTagId that = (UserTagId) o;
        return Objects.equals(userId, that.getUserId()) &&
                Objects.equals(tagId, that.getTagId());
    }

    public UserTagId(Long userId, Long tagId) {
        this.userId = userId;
        this.tagId = tagId;
    }

    public UserTagId(){}
}
