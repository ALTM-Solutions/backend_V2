package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @OneToMany(mappedBy = "typeParcours")
    @JsonIgnore
    private List<Progression> progressions;

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
