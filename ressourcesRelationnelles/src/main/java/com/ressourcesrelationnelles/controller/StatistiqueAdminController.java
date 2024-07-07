package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.dto.StatistiqueAdminDto;
import com.ressourcesrelationnelles.service.StatistiqueAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/admin/statistique")
@SecurityRequirement(name = "Authorization")
public class StatistiqueAdminController {

    /*
       Ce controller permet d'obtenir des statistiques admin
    */

    @Autowired
    private StatistiqueAdminService statistiqueAdminService;

    @GetMapping
    public StatistiqueAdminDto getStats(){
        return statistiqueAdminService.getAllStats();
    }

}
