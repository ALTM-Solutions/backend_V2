package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

// Entity :  C’est une annotation JPA utilisée pour marquer une classe comme représentant une table dans une base de données. Chaque instance de la classe représente une ligne dans cette table.
@Entity
@Table(name = "validation_utilisateur")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ValidationUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validation_utilisateur")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "code")
    @JsonIgnore
    private String code;

    @Column(name = "expiration_date")
    @JsonIgnore
    private Date dateExpiration;

    public ValidationUtilisateur() {
    }

    public ValidationUtilisateur(Integer id, String nom, String prenom, String email, String password, String code, Date dateExpiration) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.code = code;
        this.dateExpiration = dateExpiration;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
