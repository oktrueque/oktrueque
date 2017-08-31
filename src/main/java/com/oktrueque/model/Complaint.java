package com.oktrueque.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Fabrizio SPOSETTI on 31/08/2017.
 */

@Entity
@Table(name = "complaint")
public class Complaint {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String complaint;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_complaint_type")
    private ComplaintType complaintType;

    @ManyToOne
    @JoinColumn(name = "id_user_target")
    private User user_target;

    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }


    public Complaint(){};

    public Complaint(String complaint, Date date, ComplaintType complaintType, User user_target) {
        this.complaint = complaint;
        this.date = date;
        this.complaintType = complaintType;
        this.user_target = user_target;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser_target() {
        return user_target;
    }

    public void setUser_target(User user_target) {
        this.user_target = user_target;
    }
}
