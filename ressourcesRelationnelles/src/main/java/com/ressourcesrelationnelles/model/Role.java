package com.ressourcesrelationnelles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Utilisateur> utilisateurs;

    @Transient
    @JsonIgnore
    private UserType userType;

    public Role(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Role(String nom){
        this.nom = nom;
    }

    public Role(Integer id, String nom, List<Utilisateur> utilisateurs) {
        this.id = id;
        this.nom = nom;
        this.utilisateurs = utilisateurs;
    }

    public Role() {
    }

    public void setRoleGranted(){
        if(this.nom.equals(UserType.ADMIN.toString())){
            this.userType = UserType.ADMIN;
        } else if (this.nom.equals(UserType.CITIZEN.toString())) {
            this.userType = UserType.CITIZEN;
        } else if (this.nom.equals(UserType.MODERATEUR.toString())) {
            this.userType = UserType.MODERATEUR;
        } else if (this.nom.equals(UserType.SUPER_ADMIN.toString())) {
            this.userType = UserType.SUPER_ADMIN;
        }
    }
    public UserType getUserType(){
        return this.userType;
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

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
