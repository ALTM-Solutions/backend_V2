package com.ressourcesrelationnelles.dto;


/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class CreatedCommentaire {

    private String contenu;

    private Integer idRessource;

    private Integer idUtilisateur;

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Integer getIdRessource() {
        return idRessource;
    }

    public void setIdRessource(Integer idRessource) {
        this.idRessource = idRessource;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
