package com.oktrueque.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "id_trueque")
    private Long idTrueque;

    public Conversation() {
    }

    public Conversation(LocalDateTime date, Long idTrueque) {
        this.date = date;
        this.idTrueque = idTrueque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getIdTrueque() {
        return idTrueque;
    }

    public void setIdTrueque(Long idTrueque) {
        this.idTrueque = idTrueque;
    }
}
