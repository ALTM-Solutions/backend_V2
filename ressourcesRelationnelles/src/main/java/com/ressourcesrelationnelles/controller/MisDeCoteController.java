package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RessourceConnectedDto;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.MisDeCoteService;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/citoyens/mis-de-cote")
@SecurityRequirement(name = "Authorization")
public class MisDeCoteController {

    /*
       Ce controller permet d'ajouter ou retirer (supprimer) une ressource des "mis de coté"
    */

    @Autowired
    private RessourcesService ressourcesService;
    @Autowired
    private MisDeCoteService misDeCoteService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @DeleteMapping
    public void removeFavorite(@RequestParam("ressourceId") Integer ressourceId, @RequestHeader("Authorization") String token){
        Utilisateur user = authService.getUserByToken(token);
        misDeCoteService.removeMisDeCote(ressourceId,user.getId());
    }

    @PostMapping
    public void addFavorite(@RequestParam("ressourceId") Integer ressourceId, @RequestHeader("Authorization") String token){
        Utilisateur user = authService.getUserByToken(token);
        misDeCoteService.addMisDeCote(ressourceId,user.getId());
    }

    @GetMapping
    public List<RessourceConnectedDto> getAllFavorite(@RequestHeader("Authorization") String token){

        return ressourcesService.getAllRessourceMisDeCoteUser(authService.getUserByToken(token));

    }

}
