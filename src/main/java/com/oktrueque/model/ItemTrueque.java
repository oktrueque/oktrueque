package com.oktrueque.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "items_trueques")
public class ItemTrueque implements Comparable {

    @EmbeddedId
    private ItemTruequeId id;

    public ItemTrueque() {
    }

    public ItemTrueque(ItemTruequeId id) {
        this.id = id;
    }

    public ItemTruequeId getId() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        ItemTrueque other = (ItemTrueque) o;
        return this.hashCode() - other.hashCode();
    }
}
