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
@Table(name = "reponse")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "reponse")
    private String reponse;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "piece_jointe_id")
    private PieceJointe pieceJointe;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "commentaire_id", nullable = false)
    @JsonIgnore
    private Commentaire commentaire;

    public Reponse(Integer id, String reponse, Date date) {
        this.id = id;
        this.reponse = reponse;
        this.date = date;
    }

    public Reponse() {
    }

    public PieceJointe getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", reponse='" + reponse + '\'' +
                ", date=" + date +
                '}';
    }
}
