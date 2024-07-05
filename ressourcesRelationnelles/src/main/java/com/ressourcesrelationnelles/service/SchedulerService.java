package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.repository.IValidationUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SchedulerService {

    @Autowired
    private IValidationUtilisateurRepository validationUtilisateurRepository;

    public void deleteExpiredValidationRepository(){
        validationUtilisateurRepository.deleteByDateExpirationLessThan(LocalDateTime.now());
    }
}
