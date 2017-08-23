package com.oktrueque.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Facundo on 8/23/2017.
 */
@Entity
@Table(name = "items_trueques")
public class ItemTrueque implements Comparable {

    @EmbeddedId
    private ItemTruequeId id;

    public ItemTrueque() {
    }

    public ItemTruequeId getId() {
        return id;
    }

    public ItemTrueque(ItemTruequeId id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        ItemTrueque other = (ItemTrueque) o;
        return this.hashCode() - other.hashCode();
    }
}
