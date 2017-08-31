package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */

@Entity
@Table(name = "complaint_type")
public class ComplaintType {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;

    public ComplaintType() { }

    public ComplaintType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
