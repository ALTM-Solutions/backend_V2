package com.ressourcesrelationnelles.controller;


import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RessourceConnectedDto;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.FavorisService;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citoyens/favoris")
@SecurityRequirement(name = "Authorization")
public class FavorisController {

    /*
        Ce controller permet d'ajouter ou retirer (supprimer) une ressource en favoris
     */

    @Autowired
    private RessourcesService ressourcesService;

    @Autowired
    private FavorisService favorisService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @DeleteMapping
    public void removeFavorite(@RequestParam("ressourceId") Integer ressourceId, @RequestHeader("Authorization") String token){
        Utilisateur user = authService.getUserByToken(token);
        favorisService.removeFavorite(ressourceId,user.getId());
    }

    @PostMapping
    public void addFavorite(@RequestParam("ressourceId") Integer ressourceId, @RequestHeader("Authorization") String token){
        Utilisateur user = authService.getUserByToken(token);
        favorisService.addToFavorite(ressourceId,user.getId());
    }

    @GetMapping
    public List<RessourceConnectedDto> getAllFavorite(@RequestHeader("Authorization") String token){

        return ressourcesService.getAllRessourceFavorisUser(authService.getUserByToken(token));

    }


}
