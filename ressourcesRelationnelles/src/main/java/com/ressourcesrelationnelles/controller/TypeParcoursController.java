package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.model.TypeParcours;
import com.ressourcesrelationnelles.service.TypeParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("api/public/type-parcours")
public class TypeParcoursController {

    /*
       Ce controller permet d'obtenir les collcetions/type parcours
    */

    @Autowired
    private TypeParcoursService typeParcoursService;

    @GetMapping
    public List<TypeParcours> getAll(){
        return typeParcoursService.getAll();
    }

}
