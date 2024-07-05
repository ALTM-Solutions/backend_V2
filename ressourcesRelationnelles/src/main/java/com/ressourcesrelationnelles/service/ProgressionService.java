package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Progression;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IProgressionRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressionService {

    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private IProgressionRepository progressionRepository;


    public boolean addProgression(Integer ressourceId, Utilisateur utilisateur, Double progression){
        try {
            // check if user have progression on this ressource and update if already has or add if not exist
            for (Progression progress : utilisateur.getProgressions()) {
                if (progress.getRessources().getId().equals(ressourceId)) {
                    progress.setStatus(progression);
                    progressionRepository.saveAndFlush(progress);
                    return true;
                }
            }

            Ressources ressources = ressourcesRepository.getReferenceById(ressourceId);

            Progression progress = new Progression();
            progress.setStatus(progression);
            progress.setRessources(ressources);
            progress.setUtilisateur(utilisateur);

            progressionRepository.saveAndFlush(progress);

            return true;

        }catch (Exception e){
            return false;
        }

    }

}
