package com.oktrueque.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserLite{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email")
    @NotNull
    @NotEmpty
    private String email;
    @Column(name = "password")
    @NotNull
    @NotEmpty
    private String password;
    @Column(name = "status")
    private Integer status;
    @Column(name = "items_amount")
    private Integer itemsAmount;
    @Column(name = "photo1")
    private String photo1;
    @Column(name = "username")
    @NotNull
    @NotEmpty
    private String username;
    @Column(name = "score")
    private Integer score;
    @Column(name="wallpaper")
    private String wallpaper;
    @Column(name = "register_date")
    private Date registerDate;


    public UserLite() {
    }

    public UserLite(String name, String last_name, String email, String password, Integer status, Integer itemsAmount, String photo1, String username, Integer score, String wallpaper, Date registerDate) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.itemsAmount = itemsAmount;
        this.photo1 = photo1;
        this.username = username;
        this.score = score;
        this.wallpaper = wallpaper;
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(Integer itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }
}
