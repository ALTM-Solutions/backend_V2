package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type_parcours")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class TypeParcours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @ManyToMany(mappedBy = "typeParcours")
    @JsonIgnore
    private List<Ressources> ressources;

    public TypeParcours(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public TypeParcours() {

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

    @Override
    public String toString() {
        return "TypeParcours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
