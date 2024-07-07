package com.ressourcesrelationnelles.controller;


import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.ProgressionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/citoyens/progression")
@SecurityRequirement(name = "Authorization")
public class ProgressionController {

    /*
       Ce controller permet d'ajouter/modifier une progression
    */
    @Autowired
    private AuthService authService;

    @Autowired
    private ProgressionService progressionService;


    @PostMapping
    public void addProgressionRessource(@RequestParam("ressourceId") Integer ressourceId,@RequestParam("progression") Double progression, @RequestHeader("Authorization") String token){
        Utilisateur user = authService.getUserByToken(token);
        progressionService.addProgression(ressourceId,user,progression);
    }


}
