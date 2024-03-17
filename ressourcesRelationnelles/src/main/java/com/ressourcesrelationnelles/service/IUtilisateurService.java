package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface IUtilisateurService{
    Utilisateur createFromJson(Map<String, Object> json_utilisateur);
}
