package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.service.IRessourcesService;
import com.ressourcesrelationnelles.service.RessourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/ressources")
public class RessourcesController {
    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private RessourcesService ressourcesService;

    @GetMapping
    public List<Ressources> getAll(){
        return ressourcesRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    /* la RequestPart ici attend un formulaire ce qui veut dire que le formulaire qui doit être renvoyé doit
     ressembler à :
     "ressource" : {
        "texte":"Texte de la ressources"
        "nomRessource" : "Titre"
        "idUtilisateur": 3
     }
     "file" : fichier (MultipartFile
    */
    public void create(@RequestPart("ressource") CreatedRessourceDto ressourceDto, @RequestPart("file")MultipartFile file){
        ressourcesService.createFromJson(ressourceDto,file);
    }

}
