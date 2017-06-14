package com.oktrueque.model;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @OneToOne
    @JoinColumn(name = "id_category")
    private Category category;
    private String photo1;
    private String photo2;
    private String photo3;

    public Item() {
    }

    public Item(Long id, String name, String description, User user, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.category = category;
    }

    public Item(String name, String description, User user, Category category, String photo1, String photo2, String photo3) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.category = category;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
    }

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

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }
}
