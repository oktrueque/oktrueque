package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by nicolas on 19/07/17.
 */
@Entity
@Table(name = "users_tags")
public class UserTag implements Comparable {



    @EmbeddedId
    private UserTagId id;

    @Column(name = "name")
    private String name;

    public UserTagId getId() {
        return id;
    }

    public void setId(UserTagId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        UserTag other = (UserTag) o;
        return this.hashCode() - other.hashCode();
    }

    public UserTag(UserTagId id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserTag(){}

}
