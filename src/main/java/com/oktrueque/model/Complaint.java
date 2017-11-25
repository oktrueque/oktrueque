package com.oktrueque.model;

import com.oktrueque.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */

@Entity
@Table(name = "complaint")
public class Complaint {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "id_complaint_type")
    private ComplaintType complaintType;
    @ManyToOne
    @JoinColumn(name = "id_user_target")
    private User user_target;
    @ManyToOne
    @JoinColumn(name = "id_user_origin")
    private User user_origin;
    @Column(name = "status")
    private Integer status;



    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }


    public Complaint(){}

    public Complaint(String description, ComplaintType complaintType, User user_target, User user_origin) {
        this.description = description;
        this.date = LocalDateTime.now();
        this.complaintType = complaintType;
        this.user_target = user_target;
        this.user_origin = user_origin;
        this.status = Constants.COMPLAINT_STATUS_PENDING;
    }

    public User getUser_origin() {
        return user_origin;
    }

    public void setUser_origin(User user_origin) {
        this.user_origin = user_origin;
    }

    public Complaint(String description, LocalDateTime date, ComplaintType complaintType, User user_target, User user_origin) {
        this.description = description;
        this.date = date;
        this.complaintType = complaintType;
        this.user_target = user_target;
        this.user_origin = user_origin;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser_target() {
        return user_target;
    }

    public void setUser_target(User user_target) {
        this.user_target = user_target;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
