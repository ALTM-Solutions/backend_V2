package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse_mail")
    private String adresseMail;

    @Column(name = "mot_de_passe")
    @JsonIgnore
    private String motDePasse;

    @Column(name = "chemin_photo_profil")
    private String cheminPhotoProfil;

    @Column(name = "date_desactive")
    private Date dateDesactive;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "utilisateur")
    // Quand appel via CRUD, ne l'affiche pas par défaut ; provoque une boucle infinie
    @JsonIgnore
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Reponse> reponses;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Ressources> ressources;

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Progression> progressions;

    @OneToMany(mappedBy = "envoyeur")
    @JsonIgnore
    private List<Message> messagesEnvoyeur;

    @OneToMany(mappedBy = "recepteur")
    @JsonIgnore
    private List<Message> messagesRecepteur;

    public Utilisateur() {
    }

    //sans Rôle
    public Utilisateur(String nom, String prenom, String adresseMail, String motDePasse, String cheminPhotoProfil) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.cheminPhotoProfil = cheminPhotoProfil;
    }

    //sans "List"
    public Utilisateur(String nom, String prenom, String adresseMail, String motDePasse, String cheminPhotoProfil, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.cheminPhotoProfil = cheminPhotoProfil;
        this.role = role;
    }

    public Utilisateur(Integer id, String nom, String prenom, String adresseMail, String motDePasse, String cheminPhotoProfil, Date dateDesactive, Role role, List<Commentaire> commentaires, List<Ressources> ressources, List<Progression> progressions, List<Message> messagesEnvoyeur, List<Message> messagesRecepteur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.cheminPhotoProfil = cheminPhotoProfil;
        this.dateDesactive = dateDesactive;
        this.role = role;
        this.commentaires = commentaires;
        this.ressources = ressources;
        this.progressions = progressions;
        this.messagesEnvoyeur = messagesEnvoyeur;
        this.messagesRecepteur = messagesRecepteur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getCheminPhotoProfil() {
        return cheminPhotoProfil;
    }

    public void setCheminPhotoProfil(String cheminPhotoProfil) {
        this.cheminPhotoProfil = cheminPhotoProfil;
    }

    public Date getDateDesactive() {
        return dateDesactive;
    }

    public void setDateDesactive(Date dateDesactive) {
        this.dateDesactive = dateDesactive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<Ressources> getRessources() {
        return ressources;
    }

    public void setRessources(List<Ressources> ressources) {
        this.ressources = ressources;
    }

    public List<Progression> getProgressions() {
        return progressions;
    }

    public void setProgressions(List<Progression> progressions) {
        this.progressions = progressions;
    }

    public List<Message> getMessagesEnvoyeur() {
        return messagesEnvoyeur;
    }

    public void setMessagesEnvoyeur(List<Message> messagesEnvoyeur) {
        this.messagesEnvoyeur = messagesEnvoyeur;
    }

    public List<Message> getMessagesRecepteur() {
        return messagesRecepteur;
    }

    public void setMessagesRecepteur(List<Message> messagesRecepteur) {
        this.messagesRecepteur = messagesRecepteur;
    }
}
