package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    private Ressources ressources;

    @ManyToOne
    @JoinColumn(name = "type_parcours_id", nullable = false)
    private TypeParcours typeParcours;

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

    @Override
    public String toString() {
        return "Progression{" +
                "id=" + id +
                '}';
    }
}
