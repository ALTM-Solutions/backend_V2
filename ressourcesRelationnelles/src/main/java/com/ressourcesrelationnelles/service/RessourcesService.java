package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Service
public class RessourcesService implements IRessourcesService {
    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    public Ressources createFromForm(CreatedRessourceDto ressourceDto, MultipartFile file, String uri, String port){
        Ressources ressources = new Ressources();
        ressources.setNomRessource(ressourceDto.getNomRessource());
        if(!file.isEmpty()) {
            ressources.setPieceJointe(fileStorageService.createPieceJointe(file, uri, port));
        }else{
            ressources.setPieceJointe(null);
        }
        ressources.setText(ressourceDto.getTexte());
        ressources.setUtilisateur(utilisateurRepository.getReferenceById(ressourceDto.getIdUtilisateur()));
        ressources.setDatePublication(new Date());
        ressources.setDateModification(new Date());
        return ressources;
    }

    public Ressources createFromForm(CreatedRessourceDto ressourceDto){
        Ressources ressources = new Ressources();
        ressources.setNomRessource(ressourceDto.getNomRessource());
        ressources.setText(ressourceDto.getTexte());
        ressources.setUtilisateur(utilisateurRepository.getReferenceById(ressourceDto.getIdUtilisateur()));
        ressources.setDatePublication(new Date());
        ressources.setDateModification(new Date());
        return ressources;
    }
}
