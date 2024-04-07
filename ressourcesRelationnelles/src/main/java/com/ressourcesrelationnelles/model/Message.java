package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "date_envoi")
    private Date dateEnvoi;

    @ManyToOne
    @JoinColumn(name = "envoyeur", nullable = false)
    private Utilisateur envoyeur;

    @ManyToOne
    @JoinColumn(name = "recepteur", nullable = false)
    private Utilisateur recepteur;

    @ManyToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    @JsonIgnore
    private Ressources ressources;

    @ManyToOne
    @JoinColumn(name = "piece_jointe_id")
    private PieceJointe pieceJointe;

    public Message(Integer id, String message, Date dateEnvoi) {
        this.id = id;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
    }

    public Message() {

    }

    public Utilisateur getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(Utilisateur envoyeur) {
        this.envoyeur = envoyeur;
    }

    public Utilisateur getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(Utilisateur recepteur) {
        this.recepteur = recepteur;
    }

    public Ressources getRessources() {
        return ressources;
    }

    public void setRessources(Ressources ressources) {
        this.ressources = ressources;
    }

    public PieceJointe getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", dateEnvoi=" + dateEnvoi +
                '}';
    }
}
