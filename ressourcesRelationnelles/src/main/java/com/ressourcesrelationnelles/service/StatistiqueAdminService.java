package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.StatistiqueAdminDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatistiqueAdminService {


    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    public StatistiqueAdminDto getAllStats(){

        StatistiqueAdminDto statistiqueAdminDto = new StatistiqueAdminDto();

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        List<Ressources> ressourcesList = ressourcesRepository.findAll();

        List<Ressources> ressourcesValid = new ArrayList<>();
        for(Ressources ressources : ressourcesList){
            if(!ressources.isValide()) {
                ressourcesValid.add(ressources);
            }
        }

        statistiqueAdminDto.setNbUser(utilisateurList.size());
        statistiqueAdminDto.setNbRessourceCreated(ressourcesList.size());
        statistiqueAdminDto.setNbRessourceToValidated(ressourcesValid.size());

//        Map<String,Integer> ressourceByCollection = new HashMap<>();
//
//        for(Ressources ressources : ressourcesList){
//            if(ressources.getTypeParcours()) {
//                ressourcesValid.add(ressources);
//            }
//        }
//
//        statistiqueAdminDto.setNbRessourceByCollection();

        return statistiqueAdminDto;


    }

}
