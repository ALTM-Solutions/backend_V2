package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.StatistiqueCitoyenDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatistiqueCitoyenService {
    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private AuthService authService;

    public StatistiqueCitoyenDto getStats(String token){

        Utilisateur user = authService.getUserByToken(token);
        StatistiqueCitoyenDto statistiqueCitoyenDto = new StatistiqueCitoyenDto();

        statistiqueCitoyenDto.setNbRessourceFavorite(user.getFavorite().size());
        statistiqueCitoyenDto.setNbRessourceMiseDeCote(user.getMisDeCote().size());
        statistiqueCitoyenDto.setNbRessourceRead(user.getProgressions().size());

        return statistiqueCitoyenDto;

    }


}
