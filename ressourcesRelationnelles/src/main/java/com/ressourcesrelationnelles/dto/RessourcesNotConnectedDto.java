package com.ressourcesrelationnelles.dto;

import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.TypeParcours;

import java.util.List;

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
