package com.ressourcesrelationnelles.controller;


import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderateur/ressources")
@SecurityRequirement(name = "Authorization")
public class ModerateurRessourceController {


    @Autowired
    private RessourcesService ressourcesService;

    @GetMapping
    public List<Ressources> getAllRessource(){

        return ressourcesService.getAllRessourceNoFilter();

    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Ressources getOne(@PathVariable Integer id, @RequestHeader("Authorization") String token){
        return ressourcesService.getRessourceNoFilter(id);
    }

    @PostMapping(value = "disable/{id}")
    public void unvalideRessource(@PathVariable Integer id){
        ressourcesService.unvalidedRessource(id);
    }


    @PostMapping(value = "enable/{id}")
    public void valideRessource(@PathVariable Integer id){
        ressourcesService.validedRessource(id);
    }


}
