package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Commentaire;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Service
public class CommentaireService {

    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public Commentaire createFromJson(String contenu, Integer id_ressource,Integer id_utilisateur, MultipartFile file,String uri, String port){
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenu);
        Ressources ressources = ressourcesRepository.getReferenceById(id_ressource);
        commentaire.setRessources(ressources);
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(id_utilisateur);
        commentaire.setUtilisateur(utilisateur);
        commentaire.setDateCommentaire(new Date());
        if(!file.isEmpty()) {
            commentaire.setPieceJointe(fileStorageService.createPieceJointe(file, uri, port));
        }
        return commentaire;
    }
}
