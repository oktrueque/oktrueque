package com.oktrueque.model;

import javax.persistence.*;
import javax.websocket.OnError;

/**
 * Created by Facundo on 27/04/2017.
 */
@Entity
@Table(name="items")
public class Item {

    //Definicion de Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
    @OneToOne
    @JoinColumn(name="id_category")
    private Category category;


    //Constructores
    public Item() {
    }

    public Item(Long id, String name, String description, User user, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.category = category;
    }
    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
