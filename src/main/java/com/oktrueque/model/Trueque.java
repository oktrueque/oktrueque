package com.oktrueque.model;

import com.oktrueque.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "trueques")
public class Trueque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "proposal_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date proposalDate;
    @Column(name = "acceptance_date")
    private Date acceptanceDate;
    @Column(name = "rejection_date")
    private Date rejectionDate;
    @Column(name = "ending_date")
    private Date endingDate;
    @Column(name = "peopleCount")
    private Integer peopleCount;

    public Trueque() {
    }

    public Trueque(Long id){
        this.id = id;
    }

    public Trueque(Integer status) {
        this.status = status;
        this.proposalDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusValue(){
        return Constants.getTruequeStatusName(this.status);
    }

    public boolean isPendiente(){return this.status==0;}

    public boolean isActivo(){return this.status==1;}

    public boolean isEliminado(){return this.status==2;}

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }
}
