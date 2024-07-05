package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UtilisateurService implements IUtilisateurService{
    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // TODO : implémenter cette fonction dans le register et le update utilisateur


    // Modification profil
    public Utilisateur createFromForm(String adresseMail,String nom, String prenom, MultipartFile file) throws Exception{
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setAdresseMail(adresseMail);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        if(!file.isEmpty()){
            String fileType = fileStorageService.getFileType(file.getOriginalFilename());
            // Permet de récupérer le type du fichier pour vérifier que ce soit bien une image qui soit insérer
            String[] splited = fileType.split("/");
            String type = splited[0];
            if(type.equals("image")) {
                String filename = fileStorageService.storeFile(file);
                utilisateur.setCheminPhotoProfil(filename);
            }else{
                throw new Exception("file profil picture not a image type");
            }
        }

        return utilisateur;
    }

}
