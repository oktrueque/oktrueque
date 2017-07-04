package com.oktrueque.model;

import javax.persistence.*;


/**
 * Created by Fabrizio SPOSETTI on 03/07/2017.
 */

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "id_user_origin")
    private User user_origin;

    @ManyToOne
    @JoinColumn(name = "id_user_target")
    private User user_target;
    private int score;
    private String date;


    public Comment(String comment, User user_target, User user_origin, int score, String date) {
        this.comment = comment;
        this.user_target = user_target;
        this.user_origin = user_origin;
        this.score = score;
        this.date = date;
    }

    public Comment(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser_target() {
        return user_target;
    }

    public void setUser_target(User user_target) {
        this.user_target = user_target;
    }

    public User getUser_origin() {
        return user_origin;
    }

    public void setUser_origin(User user_origin) {
        this.user_origin = user_origin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
