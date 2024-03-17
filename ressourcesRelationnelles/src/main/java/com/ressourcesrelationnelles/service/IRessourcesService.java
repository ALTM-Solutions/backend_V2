package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IRessourcesService {
    Ressources createFromJson(CreatedRessourceDto ressourceDto, MultipartFile file);
}
