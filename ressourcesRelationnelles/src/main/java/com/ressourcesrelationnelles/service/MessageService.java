package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Message;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Message createFromForm(String text_message, Integer id_envoyeur,Integer id_recepteur, Integer id_ressource,MultipartFile file,String uri, String port){
        Message message = new Message();
        message.setMessage(text_message);
        message.setDateEnvoi(new Date());
        Utilisateur envoyeur = utilisateurRepository.getReferenceById(id_envoyeur);
        Utilisateur recepteur = utilisateurRepository.getReferenceById(id_recepteur);
        message.setEnvoyeur(envoyeur);
        message.setRecepteur(recepteur);
        Ressources ressources = ressourcesRepository.getReferenceById(id_ressource);
        message.setRessources(ressources);
        if(!file.isEmpty()){
            message.setPieceJointe(fileStorageService.createPieceJointe(file,uri,port));
        }
        return message;

    }

}
