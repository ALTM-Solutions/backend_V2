package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IUtilisateurService{
    Utilisateur createFromForm(String adresseMail,String oldAdresseMail, String nom, String prenom, MultipartFile file, String uri,String port) throws Exception;
}
