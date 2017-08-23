package com.oktrueque.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Envy on 14/6/2017.
 */
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
    private LocalDateTime proposalDate;
    @Column(name = "acceptance_date")
    private LocalDateTime acceptanceDate;
    @Column(name = "rejection_date")
    private LocalDateTime rejectionDate;
    @Column(name = "ending_date")
    private LocalDateTime endingDate;
    @Column(name = "peopleCount")
    private Integer peopleCount;

    public Trueque() {
    }

    public Trueque(Integer status) {
        this.status = status;
        this.proposalDate = LocalDateTime.now();
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(LocalDateTime proposalDate) {
        this.proposalDate = proposalDate;
    }

    public LocalDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }
}
