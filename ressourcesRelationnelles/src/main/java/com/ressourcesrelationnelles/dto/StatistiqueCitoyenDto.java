package com.ressourcesrelationnelles.dto;

import java.util.Map;
/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class StatistiqueCitoyenDto {

    private Map<String,Double> progressByCollection;

    private Integer nbRessourceRead;

    private Integer nbRessourceFavorite;

    private Integer nbRessourceMiseDeCote;

    public StatistiqueCitoyenDto() {
    }

    public StatistiqueCitoyenDto(Map<String, Double> progressByCollection, Integer nbRessourceRead, Integer nbRessourceFavorite, Integer nbRessourceMiseDeCote) {
        this.progressByCollection = progressByCollection;
        this.nbRessourceRead = nbRessourceRead;
        this.nbRessourceFavorite = nbRessourceFavorite;
        this.nbRessourceMiseDeCote = nbRessourceMiseDeCote;
    }

    public Map<String, Double> getProgressByCollection() {
        return progressByCollection;
    }

    public void setProgressByCollection(Map<String, Double> progressByCollection) {
        this.progressByCollection = progressByCollection;
    }

    public Integer getNbRessourceRead() {
        return nbRessourceRead;
    }

    public void setNbRessourceRead(Integer nbRessourceRead) {
        this.nbRessourceRead = nbRessourceRead;
    }

    public Integer getNbRessourceFavorite() {
        return nbRessourceFavorite;
    }

    public void setNbRessourceFavorite(Integer nbRessourceFavorite) {
        this.nbRessourceFavorite = nbRessourceFavorite;
    }

    public Integer getNbRessourceMiseDeCote() {
        return nbRessourceMiseDeCote;
    }

    public void setNbRessourceMiseDeCote(Integer nbRessourceMiseDeCote) {
        this.nbRessourceMiseDeCote = nbRessourceMiseDeCote;
    }
}
