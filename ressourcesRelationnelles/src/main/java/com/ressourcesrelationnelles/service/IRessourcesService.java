package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IRessourcesService {
    Ressources createFromForm(CreatedRessourceDto ressourceDto, MultipartFile file,String uri, String port);
}
