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
public class User_Tag implements Comparable {

    @EmbeddedId
    private User_Tag_Id id;

    @Column(name = "name")
    private String name;

    public User_Tag_Id getId() {
        return id;
    }

    public void setId(User_Tag_Id id) {
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
        User_Tag other = (User_Tag) o;
        return this.hashCode() - other.hashCode();
    }
}
