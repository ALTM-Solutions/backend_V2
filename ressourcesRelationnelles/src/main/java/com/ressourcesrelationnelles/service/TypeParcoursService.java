package com.ressourcesrelationnelles.service;


import com.ressourcesrelationnelles.model.TypeParcours;
import com.ressourcesrelationnelles.repository.ITypeParcoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeParcoursService {

    @Autowired
    private ITypeParcoursRepository typeParcoursRepository;

    @Autowired
    private UtilsService utils;

    public boolean createTypeParcours(String nom){

        if(!typeParcoursRepository.existsByNom(utils.escapeHtml(nom))){

            TypeParcours typeParcours = new TypeParcours();
            typeParcours.setNom(utils.escapeHtml(nom));

            typeParcoursRepository.saveAndFlush(typeParcours);
            return true;

        }
        return false;
    }

    public void updateTypeParcours(String nom, Integer id) throws Exception{

        if(!typeParcoursRepository.existsByNom(utils.escapeHtml(nom))){
            TypeParcours typeParcours = typeParcoursRepository.getReferenceById(id);
            typeParcours.setNom(utils.escapeHtml(nom));
            typeParcoursRepository.saveAndFlush(typeParcours);

        }else{
           throw new Exception("Type parcours not exist");
        }
    }

    public void deleteParcours(String nom){

        if(typeParcoursRepository.existsByNom(nom)){

            typeParcoursRepository.delete(typeParcoursRepository.findByNom(nom));

        }

    }
    public void deleteParcours(Integer id){

        typeParcoursRepository.deleteById(id);

    }

    public TypeParcours getOne(Integer id){

        return typeParcoursRepository.getReferenceById(id);
    }

    public List<TypeParcours> getAll(){

        return typeParcoursRepository.findAll();

    }

    public List<TypeParcours> transformStringToList(String listTypeParcours){

        List<TypeParcours> list = new ArrayList<>();
        if(listTypeParcours.isEmpty()){
            return list;
        }else{
            for(String id : listTypeParcours.split(",")){
                list.add(typeParcoursRepository.getReferenceById(Integer.valueOf(id)));
            }
            return list;
        }

    }

}
