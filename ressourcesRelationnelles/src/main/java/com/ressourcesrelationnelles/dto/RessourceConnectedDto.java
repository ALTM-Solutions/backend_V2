package com.ressourcesrelationnelles.dto;

import com.ressourcesrelationnelles.model.Progression;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.TypeParcours;

import java.util.List;
/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class RessourceConnectedDto {
    private Ressources ressources;

    private Progression progression;

    private List<TypeParcours> typeParcours;

    private Boolean favoris;

    private Boolean mis_de_cote;

    public RessourceConnectedDto(Ressources ressources, Progression progression, List<TypeParcours> typeParcours, Boolean favoris, Boolean mis_de_cote) {
        this.ressources = ressources;
        this.progression = progression;
        this.typeParcours = typeParcours;
        this.favoris = favoris;
        this.mis_de_cote = mis_de_cote;
    }

    public Boolean getFavoris() {
        return favoris;
    }

    public void setFavoris(Boolean favoris) {
        this.favoris = favoris;
    }

    public Boolean getMis_de_cote() {
        return mis_de_cote;
    }

    public void setMis_de_cote(Boolean mis_de_cote) {
        this.mis_de_cote = mis_de_cote;
    }

    public List<TypeParcours> getTypeParcours() {
        return typeParcours;
    }

    public void setTypeParcours(List<TypeParcours> typeParcours) {
        this.typeParcours = typeParcours;
    }

    public Ressources getRessources() {
        return ressources;
    }

    public void setRessources(Ressources ressources) {
        this.ressources = ressources;
    }

    public Progression getProgression() {
        return progression;
    }

    public void setProgression(Progression progression) {
        this.progression = progression;
    }
}
