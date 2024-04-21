package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ressource")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ressources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_publication")
    private Date datePublication;

    @Column(name = "date_modification")
    private Date dateModification;

    @Column(name = "text")
    private String text;

    @Column(name = "visibilite")
    private boolean visibilite;

    @Column(name = "valide")
    private boolean valide;

    @Column(name = "nom_ressource")
    private String nomRessource;

    @OneToMany(mappedBy = "ressources")
    private List<Commentaire> commentaires;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "piece_jointe_id")
    private PieceJointe pieceJointe;

    @OneToMany(mappedBy = "ressources")
    @JsonIgnore
    private List<Progression> progressions;

    @OneToMany(mappedBy = "ressources")
    @JsonIgnore
    private List<Message> messages;

    //Constructeur sans les "List" pour le Create
    public Ressources(Integer id, Date datePublication, Date dateModification, String text, boolean visibilite, boolean valide, String nomRessource, Utilisateur utilisateur, PieceJointe pieceJointe) {
        this.id = id;
        this.datePublication = datePublication;
        this.dateModification = dateModification;
        this.text = text;
        this.visibilite = visibilite;
        this.valide = valide;
        this.nomRessource = nomRessource;
        this.utilisateur = utilisateur;
        this.pieceJointe = pieceJointe;
    }

    //Constructeur sans PJ pour le Create
    public Ressources(Integer id, Date datePublication, Date dateModification, String text, boolean visibilite, boolean valide, String nomRessource, Utilisateur utilisateur) {
        this.id = id;
        this.datePublication = datePublication;
        this.dateModification = dateModification;
        this.text = text;
        this.visibilite = visibilite;
        this.valide = valide;
        this.nomRessource = nomRessource;
        this.utilisateur = utilisateur;
    }

    public Ressources(Integer id, Date datePublication, Date dateModification, String text, boolean visibilite, boolean valide, String nomRessource, List<Commentaire> commentaires, Utilisateur utilisateur, PieceJointe pieceJointe, List<Progression> progressions, List<Message> messages) {
        this.id = id;
        this.datePublication = datePublication;
        this.dateModification = dateModification;
        this.text = text;
        this.visibilite = visibilite;
        this.valide = valide;
        this.nomRessource = nomRessource;
        this.commentaires = commentaires;
        this.utilisateur = utilisateur;
        this.pieceJointe = pieceJointe;
        this.progressions = progressions;
        this.messages = messages;
    }

    public Ressources() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isVisibilite() {
        return visibilite;
    }

    public void setVisibilite(boolean visibilite) {
        this.visibilite = visibilite;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public String getNomRessource() {
        return nomRessource;
    }

    public void setNomRessource(String nomRessource) {
        this.nomRessource = nomRessource;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public PieceJointe getPieceJointe() {
        return pieceJointe;
    }

    public void setPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public List<Progression> getProgressions() {
        return progressions;
    }

    public void setProgressions(List<Progression> progressions) {
        this.progressions = progressions;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
