package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RessourcesNotConnectedDto;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/public/ressources")
@SecurityRequirement(name = "Authorization")
public class RessourcesController {

    /*
       Ce controller permet de récupérer les ressources
    */
    
    @Autowired
    private RessourcesService ressourcesService;

    @GetMapping
    public List<RessourcesNotConnectedDto> getAll(){
        return ressourcesService.getAllRessourceNotConnected();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RessourcesNotConnectedDto getOne(@PathVariable Integer id){
        return ressourcesService.getOneRessourceNotConnected(id);
    }
}
