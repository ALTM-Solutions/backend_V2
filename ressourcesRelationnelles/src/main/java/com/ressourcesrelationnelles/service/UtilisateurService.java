package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UtilisateurService implements IUtilisateurService{
    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public Utilisateur createFromJson(Map<String, Object> json_utilisateur){
        String prenom = (String) json_utilisateur.get("prenom");
        String nom = (String) json_utilisateur.get("nom");
        String mot_de_passe = (String) json_utilisateur.get("mot_de_passe");
        String chemin_photo_profil = (String) json_utilisateur.get("chemin_photo_profil");
        String adresse_mail = (String) json_utilisateur.get("adresse_mail");
        Role role = roleRepository.findByNom("citoyen").get(0);
        return new Utilisateur(nom,prenom,adresse_mail,mot_de_passe,chemin_photo_profil,role);
    }
}
