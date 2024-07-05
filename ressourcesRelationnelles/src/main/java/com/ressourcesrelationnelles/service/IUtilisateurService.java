package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Utilisateur;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUtilisateurService{
    Utilisateur createFromForm(String adresseMail, String nom, String prenom, MultipartFile file) throws Exception;
}
