package com.oktrueque.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user_origin")
    private UserLite user_origin;

    @ManyToOne
    @JoinColumn(name = "id_user_target")
    private UserLite user_target;
    private int score;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    public Comment(String description, UserLite user_origin, UserLite user_target, int score, LocalDateTime date) {
        this.description = description;
        this.user_origin = user_origin;
        this.user_target = user_target;
        this.score = score;
        this.date = date;
    }

    public Comment(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserLite getUser_target() {
        return user_target;
    }

    public void setUser_target(UserLite user_target) {
        this.user_target = user_target;
    }

    public UserLite getUser_origin() {
        return user_origin;
    }

    public void setUser_origin(UserLite user_origin) {
        this.user_origin = user_origin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
