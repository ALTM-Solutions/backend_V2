package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.TypeParcours;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRessourcesService {
    Ressources createFromForm(CreatedRessourceDto ressourceDto, MultipartFile file, List<TypeParcours> listTypeParcours);
}
