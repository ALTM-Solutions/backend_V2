package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "piece_jointe")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "chemin_piece_jointe")
    private String cheminPieceJointe;

    @Column(name = "nom_origine")
    private String nomOrigin;

    @OneToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<Ressources> ressources;

    @OneToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<Message> messages;

    @OneToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<Reponse> reponses;

    public PieceJointe(Integer id, String cheminPieceJointe) {
        this.id = id;
        this.cheminPieceJointe = cheminPieceJointe;
    }

    public PieceJointe(Integer id, String cheminPieceJointe, String nomOrigin) {
        this.id = id;
        this.cheminPieceJointe = cheminPieceJointe;
        this.nomOrigin = nomOrigin;
    }

    public PieceJointe() {

    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheminPieceJointe() {
        return cheminPieceJointe;
    }

    public void setCheminPieceJointe(String cheminPieceJointe) {
        this.cheminPieceJointe = cheminPieceJointe;
    }

    public String getNomOrigin() {
        return nomOrigin;
    }

    public void setNomOrigin(String nomOrigin) {
        this.nomOrigin = nomOrigin;
    }

    @Override
    public String toString() {
        return "PieceJointe{" +
                "id=" + id +
                ", cheminPieceJointe='" + cheminPieceJointe + '\'' +
                '}';
    }
}
