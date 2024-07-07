package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.model.TypeParcours;
import com.ressourcesrelationnelles.service.TypeParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("api/admin/type-parcours")
public class AdminTypeParcoursController {


    /*
        Ce controller permet à l'administrateur/super admin de créer/obtenir/supprimer des types de parcours (collections)
     */

    @Autowired
    private TypeParcoursService typeParcoursService;

    @GetMapping
    public List<TypeParcours> getAll(){
        return typeParcoursService.getAll();
    }

    @GetMapping(value = "/{id}")
    public TypeParcours getOne(@PathVariable("id") Integer id){

        return typeParcoursService.getOne(id);

    }

    @PostMapping
    public void createTypeParcorus(@RequestParam("nom") String nom){

        typeParcoursService.createTypeParcours(nom);

    }

    @PutMapping(value = "/{id}")
    public void updateTypeParcours(@RequestParam("nom") String nom,@PathVariable("id") Integer id){
        try {
            typeParcoursService.updateTypeParcours(nom,id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTypeParcours(@PathVariable("id") Integer id){
        typeParcoursService.deleteParcours(id);
    }

}
