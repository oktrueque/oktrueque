package com.oktrueque.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user_origin")
    private UserLite userOrigin;

    @ManyToOne
    @JoinColumn(name = "id_user_target")
    private UserLite userTarget;
    private int score;

    private Date date;

    public Comment(String description, UserLite userOrigin, UserLite userTarget, int score, Date date) {
        this.description = description;
        this.userOrigin = userOrigin;
        this.userTarget = userTarget;
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

    public UserLite getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(UserLite userTarget) {
        this.userTarget = userTarget;
    }

    public UserLite getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(UserLite userOrigin) {
        this.userOrigin = userOrigin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
