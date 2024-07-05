package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.model.TypeParcours;
import com.ressourcesrelationnelles.service.TypeParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/public/type-parcours")
public class TypeParcoursController {

    @Autowired
    private TypeParcoursService typeParcoursService;

    @GetMapping
    public List<TypeParcours> getAll(){
        return typeParcoursService.getAll();
    }

}
