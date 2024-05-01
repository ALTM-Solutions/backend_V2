package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.config.SecurityConstants;
import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


@Service
public class UtilisateurService implements IUtilisateurService{
    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // TODO : implémenter cette fonction dans le register et le update utilisateur
    public Boolean isSecuredPassword(String password){
        if (password.length() <= SecurityConstants.PASSWORD_SIZE) {
            return false;
        }

        // Vérifie si le mot de passe contient au moins une majuscule
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        // Vérifie si le mot de passe contient au moins un chiffre
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        // Vérifie si le mot de passe contient au moins un caractère spécial
        if (!Pattern.compile("[@!?;,_-]").matcher(password).find()) {
            return false;
        }

        // Si toutes les conditions sont remplies, retourne true
        return true;
    }

    // Modification profil
    public Utilisateur createFromForm(String adresseMail,String oldAdresseMail, String nom, String prenom, MultipartFile file, String uri, String port) throws Exception{
        Utilisateur utilisateur = new Utilisateur();
        if(oldAdresseMail.equals(adresseMail)){
            utilisateur.setAdresseMail(adresseMail);
        }else {
            if (!utilisateurRepository.existsByAdresseMail(adresseMail)) {
                utilisateur.setAdresseMail(adresseMail);
            } else {
                throw new Exception("This adresse mail already exist");
            }
        }
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        if(!file.isEmpty()){
            String fileType = fileStorageService.getFileType(file.getOriginalFilename());
            // Permet de récupérer le type du fichier pour vérifier que ce soit bien une image qui soit insérer
            String[] splited = fileType.split("/");
            String type = splited[0];
            if(type.equals("image")) {
                String filename = fileStorageService.storeFile(file);
                String fullPath = uri + ":" + port + "/api/public/file/downloadFile/" + filename;
                utilisateur.setCheminPhotoProfil(fullPath);
            }else{
                throw new Exception("file profil picture not a image type");
            }
        }

        return utilisateur;
    }

}
