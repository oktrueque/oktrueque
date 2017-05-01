package com.oktrueque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Facundo on 01/05/2017.
 */
@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;

    //Constructores
    public User() {
    }

    public User(Long id, String name, String last_name, String email, String password) {
        this.id = id;
        this.name = name;
        this.lastName = last_name;
        this.email = email;
        this.password = password;
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

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
