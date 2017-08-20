package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemTagId implements Serializable{

    @Column(name = "id_item")
    private Long itemId;
    @Column(name = "id_tag")
    private Long tagId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, tagId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTagId)) return false;
        ItemTagId that = (ItemTagId) o;
        return Objects.equals(itemId, that.getItemId()) &&
                Objects.equals(tagId, that.getTagId());
    }

    public ItemTagId(Long itemId, Long tagId) {
        this.itemId = itemId;
        this.tagId = tagId;
    }

    public ItemTagId() {
    }
}
