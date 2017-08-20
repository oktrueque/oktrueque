package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by nicolas on 19/07/17.
 */
@Entity
@Table(name = "items_tags")
public class ItemTag implements Comparable {

    @EmbeddedId
    private ItemTagId id;

    @Column(name = "name")
    private String name;

    public ItemTagId getId() {
        return id;
    }

    public void setId(ItemTagId id) {
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
        ItemTag other = (ItemTag) o;
        return this.hashCode() - other.hashCode();
    }

    public ItemTag(ItemTagId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemTag() {
    }
}
