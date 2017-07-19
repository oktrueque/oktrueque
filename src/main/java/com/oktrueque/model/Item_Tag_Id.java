package com.oktrueque.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Item_Tag_Id implements Serializable{

    private Long itemId;
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
        if (!(o instanceof Item_Tag_Id)) return false;
        Item_Tag_Id that = (Item_Tag_Id) o;
        return Objects.equals(itemId, that.getItemId()) &&
                Objects.equals(tagId, that.getTagId());
    }
}
