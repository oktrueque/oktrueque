package com.oktrueque.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemTruequeId implements Serializable{

    @ManyToOne
    @JoinColumn(name = "id_trueque")
    private Trueque trueque;
    @ManyToOne
    @JoinColumn(name = "id_item")
    private Item item;

    public ItemTruequeId() {
    }

    public ItemTruequeId(Trueque trueque, Item item) {
        this.trueque = trueque;
        this.item = item;
    }

    public ItemTruequeId(Long idTrueque, Long idItem){
        this.trueque = new Trueque(idTrueque);
        this.item = new Item(idItem);
    }

    public Trueque getTrueque() {
        return trueque;
    }

    public void setTrueque(Trueque trueque) {
        this.trueque = trueque;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTruequeId)) return false;
        ItemTruequeId that = (ItemTruequeId) o;
        return Objects.equals(trueque.getId(), that.getTrueque().getId()) &&
                Objects.equals(item.getId(), that.getItem().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(trueque.getId(), item.getId());
    }



}