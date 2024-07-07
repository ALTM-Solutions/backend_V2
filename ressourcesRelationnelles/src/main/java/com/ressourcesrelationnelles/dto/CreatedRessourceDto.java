package com.ressourcesrelationnelles.dto;

/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class CreatedRessourceDto {
    private String texte;
    private String nomRessource;

    private Integer idUtilisateur;


    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getNomRessource() {
        return nomRessource;
    }

    public void setNomRessource(String nomRessource) {
        this.nomRessource = nomRessource;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
