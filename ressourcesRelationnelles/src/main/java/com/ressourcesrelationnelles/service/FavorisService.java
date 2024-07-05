package com.ressourcesrelationnelles.service;


import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorisService {


    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;


    public boolean addToFavorite(Integer ressourceId, Integer utilisateurId){

        try {
            Utilisateur utilisateur = utilisateurRepository.getReferenceById(utilisateurId);

            Ressources ressources = ressourcesRepository.getReferenceById(ressourceId);

            utilisateur.addToFavorite(ressources);

            utilisateurRepository.save(utilisateur);

            return true;

        }catch (Exception e){

            return false;
        }

    }

    public boolean removeFavorite(Integer ressourceId, Integer utilisateurId){
        try {
            Utilisateur utilisateur = utilisateurRepository.getReferenceById(utilisateurId);

            for (Ressources res : utilisateur.getFavorite()) {
                if(res.getId().equals(ressourceId)){
                    utilisateur.getFavorite().remove(res);
                    utilisateurRepository.saveAndFlush(utilisateur);
                   return true;
                }
            }

            return false;

        }catch (Exception e){

            return false;
        }
    }


}
