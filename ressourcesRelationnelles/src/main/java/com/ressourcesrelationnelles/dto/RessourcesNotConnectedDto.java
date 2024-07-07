package com.ressourcesrelationnelles.dto;

import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.TypeParcours;

import java.util.List;
/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class RessourcesNotConnectedDto {

    private Ressources ressources;

    private List<TypeParcours> typeParcours;

    public RessourcesNotConnectedDto(Ressources ressources, List<TypeParcours> typeParcours) {
        this.ressources = ressources;
        this.typeParcours = typeParcours;
    }

    public RessourcesNotConnectedDto() {
    }

    public Ressources getRessources() {
        return ressources;
    }

    public void setRessources(Ressources ressources) {
        this.ressources = ressources;
    }

    public List<TypeParcours> getTypeParcours() {
        return typeParcours;
    }

    public void setTypeParcours(List<TypeParcours> typeParcours) {
        this.typeParcours = typeParcours;
    }
}
