package com.oktrueque.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Facundo on 27/04/2017.
 */
@Entity
public class Item {

    //Definicion de Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    //Por ahora declaro un User como string
    //Falta la relacion JPA ManyToOne
    private String user;
    //Por ahora declaro una categoria como string
    //Falta la relacion JPA OneToOne
    private String category;


    //Constructores
    public Item() {

    }
    public Item(long id, String name, String description, String user, String categoria) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.category = categoria;
    }

    //Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
