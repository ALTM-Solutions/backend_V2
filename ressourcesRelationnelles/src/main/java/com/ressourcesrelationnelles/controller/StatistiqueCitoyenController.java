package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.dto.StatistiqueCitoyenDto;
import com.ressourcesrelationnelles.service.StatistiqueCitoyenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/citoyen/statistique")
@SecurityRequirement(name = "Authorization")
public class StatistiqueCitoyenController {

    /*
       Ce controller permet d'obtenir des statistiques citoyen
    */

    @Autowired
    private StatistiqueCitoyenService statistiqueCitoyenService;

    @GetMapping
    public StatistiqueCitoyenDto getStats(@RequestHeader("Authorization") String token){
        return statistiqueCitoyenService.getStats(token);
    }



}
