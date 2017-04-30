package com.oktrueque.model;

import javax.persistence.*;

/**
 * Created by Facundo on 27/04/2017.
 */
@Entity
@Table(name="items")
public class Item {

    //Definicion de Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    //Por ahora declaro un User como string
    //Falta la relacion JPA ManyToOne
    private long id_user;
    //Por ahora declaro una categoria como string
    //Falta la relacion JPA OneToOne
    private int id_category;



    //Constructores
    public Item() {

    }
    public Item(long id, String name, String description, long id_user, int id_category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.id_user = id_user;
        this.id_category = id_category;
    }

    //Getters and Setters

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

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
