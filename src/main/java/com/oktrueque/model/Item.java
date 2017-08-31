package com.oktrueque.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @Column(name = "status")
    private Integer status;
    @Column(name = "photo1")
    private String photo1;
    @Column(name = "photo2")
    private String photo2;
    @Column(name = "photo3")
    private String photo3;
    @Transient
    private List<Tag> tags;
    @Transient
    private ArrayList<Long> idTags;

    public Item() {
    }

    public Item(int status) {
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getStatusValue(){
        switch (this.status){
            case 0: return "Pendiente";
            case 1: return "Activo";
            case 2: return "Eliminado";
            case 3: return "Bloqueado";
            default: return "Sin definir";
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Long> getIdTags() {
        return idTags;
    }

    public void setIdTags(ArrayList<Long> idTags) {
        this.idTags = idTags;
    }
}
