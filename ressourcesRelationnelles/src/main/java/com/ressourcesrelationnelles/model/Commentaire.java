package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "commentaire")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contenu")
    private String contenu;

    @Column(name = "date_commentaire")
    private Date dateCommentaire;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    private Ressources ressources;

    @ManyToOne
    @JoinColumn(name = "piece_jointe_id")
    private PieceJointe pieceJointe;

    public Commentaire(Integer id, String contenu, Date dateCommentaire) {
        this.id = id;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
    }

    public Commentaire() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(Date dateCommentaire) {
        this.dateCommentaire = dateCommentaire;

    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", dateCommentaire=" + dateCommentaire +
                '}';
    }
}
