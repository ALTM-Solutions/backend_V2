package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Commentaire;
import com.ressourcesrelationnelles.model.Reponse;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.ICommentaireRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ReponseService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ICommentaireRepository commentaireRepository;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilsService utils;

    public Reponse createFromForm(String text, Integer id_commentaire, Integer id_utilisateur,MultipartFile file){
        Reponse reponse = new Reponse();
        reponse.setReponse(utils.escapeHtml(text));
        reponse.setDate(new Date());
        Commentaire commentaire = commentaireRepository.getReferenceById(id_commentaire);
        reponse.setCommentaire(commentaire);
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(id_utilisateur);
        reponse.setUtilisateur(utilisateur);
        if(!file.isEmpty()) {
            reponse.setPieceJointe(fileStorageService.createPieceJointe(file));
        }
        return reponse;
    }

}
