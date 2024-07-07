package com.ressourcesrelationnelles.dto;

import java.util.ArrayList;
import java.util.Map;
/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class StatistiqueAdminDto {

    private Integer NbRessourceCreated;

    private Integer NbRessourceToValidated;

    private Map<String,Integer> NbRessourceByCollection;

    private Integer NbUser;

    private Map<String, ArrayList<String>> bestWordByCollection;

    public StatistiqueAdminDto() {
    }

    public StatistiqueAdminDto(Integer nbRessourceCreated, Integer nbRessourceToValidated, Map<String, Integer> nbRessourceByCollection, Integer nbUser, Map<String, ArrayList<String>> bestWordByCollection) {
        NbRessourceCreated = nbRessourceCreated;
        NbRessourceToValidated = nbRessourceToValidated;
        NbRessourceByCollection = nbRessourceByCollection;
        NbUser = nbUser;
        this.bestWordByCollection = bestWordByCollection;
    }

    public Integer getNbRessourceCreated() {
        return NbRessourceCreated;
    }

    public void setNbRessourceCreated(Integer nbRessourceCreated) {
        NbRessourceCreated = nbRessourceCreated;
    }

    public Integer getNbRessourceToValidated() {
        return NbRessourceToValidated;
    }

    public void setNbRessourceToValidated(Integer nbRessourceToValidated) {
        NbRessourceToValidated = nbRessourceToValidated;
    }

    public Map<String, Integer> getNbRessourceByCollection() {
        return NbRessourceByCollection;
    }

    public void setNbRessourceByCollection(Map<String, Integer> nbRessourceByCollection) {
        NbRessourceByCollection = nbRessourceByCollection;
    }

    public Integer getNbUser() {
        return NbUser;
    }

    public void setNbUser(Integer nbUser) {
        NbUser = nbUser;
    }

    public Map<String, ArrayList<String>> getBestWordByCollection() {
        return bestWordByCollection;
    }

    public void setBestWordByCollection(Map<String, ArrayList<String>> bestWordByCollection) {
        this.bestWordByCollection = bestWordByCollection;
    }
}
