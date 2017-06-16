package com.oktrueque.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Envy on 14/6/2017.
 */
@Entity
@Table(name = "trueque")
public class Trueque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user_offerer")
    private User userOfferer;
    @ManyToOne
    @JoinColumn(name = "id_user_demandant")
    private User userDemandant;
    @Column(name = "status")
    private Integer status;
    @Column(name = "proposal_date")
    @Temporal(TemporalType.DATE)
    private Date proposalDate;
    @Column(name = "acceptance_date")
    @Temporal(TemporalType.DATE)
    private Date acceptanceDate;
    @Column(name = "rejection_date")
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;
    @Column(name = "ending_date")
    @Temporal(TemporalType.DATE)
    private Date endingDate;

    public Trueque(){}

    public Trueque(User userOfferer, User userDemandant, Integer status) {
        this.userOfferer = userOfferer;
        this.userDemandant = userDemandant;
        this.status = status;
        this.proposalDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserOfferer() {
        return userOfferer;
    }

    public void setUserOfferer(User userOfferer) {
        this.userOfferer = userOfferer;
    }

    public User getUserDemandant() {
        return userDemandant;
    }

    public void setUserDemandant(User userDemandant) {
        this.userDemandant = userDemandant;
    }

    public Integer getStatus() {
        return status;
    }

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
}
