package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Facundo on 8/23/2017.
 */

@Embeddable
public class ItemTruequeId implements Serializable{

    @Column(name = "id_trueque")
    private Long truequeId;
    @Column(name = "id_item")
    private Long itemId;

    public ItemTruequeId() {
    }

    public ItemTruequeId(Long truequeId, Long itemId) {
        this.truequeId = truequeId;
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
        if (!(o instanceof ItemTruequeId)) return false;
        ItemTruequeId that = (ItemTruequeId) o;
        return Objects.equals(truequeId, that.getTruequeId()) &&
                Objects.equals(itemId, that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(truequeId, itemId);
    }



}