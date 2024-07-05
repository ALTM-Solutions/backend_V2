package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "progression")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    @JsonIgnore
    private Ressources ressources;

    @Column(name = "statut")
    private Double status;

    public Progression(Integer id) {
        this.id = id;
    }

    public Progression() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Ressources getRessources() {
        return ressources;
    }

    public void setRessources(Ressources ressources) {
        this.ressources = ressources;
    }

    public Double getStatus() {
        return status;
    }

    public void setStatus(Double status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Progression{" +
                "id=" + id +
                '}';
    }
}
