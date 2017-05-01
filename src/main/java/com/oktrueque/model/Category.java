package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Facundo on 01/05/2017.
 */
@Entity
@Table(name="categories")
public class Category {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;

    //Constructores
    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
