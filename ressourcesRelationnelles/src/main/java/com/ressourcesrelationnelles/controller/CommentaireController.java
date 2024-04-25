package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.model.Commentaire;
import com.ressourcesrelationnelles.model.Reponse;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.ICommentaireRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.CommentaireService;
import com.ressourcesrelationnelles.service.FileStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citoyens/commentaire")
@SecurityRequirement(name = "Authorization")
public class CommentaireController {

    // TODO : Supprimer le "idUtilisateur" il doit venir du Token

    private final String port;

    private final String uri;
    @Autowired
    public CommentaireController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }
    @Autowired
    private ICommentaireRepository commentaireRepository;

    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private IUtilisateurRepository utilisateurRepository;
    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private AuthService authService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Commentaire create(@RequestParam("contenu") String contenu,@RequestParam("idRessource") Integer id_ressource,
                       @RequestHeader("Authorization") String token,@RequestParam("file") MultipartFile file){

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));;
        Commentaire commentaire = commentaireService.createFromJson(contenu,id_ressource,utilisateur.getId(),file,uri,port);
        return commentaireRepository.saveAndFlush(commentaire);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id,@RequestHeader("Authorization") String token) throws Exception{
        Commentaire commentaire = commentaireRepository.getReferenceById(id);
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        utilisateur.getRole().setRoleGranted();
        if(!authService.IsAuthorize(commentaire.getUtilisateur(),utilisateur,Arrays.asList(UserType.SUPER_ADMIN,UserType.ADMIN, UserType.MODERATEUR))){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource");
        }

        for(Reponse reponse : commentaire.getReponses()){
            if(reponse.getPieceJointe() != null){
                fileStorageService.deleteFileFromUrl(reponse.getPieceJointe().getCheminPieceJointe());
            }
        }
        if(commentaire.getPieceJointe() != null){
            fileStorageService.deleteFileFromUrl(commentaire.getPieceJointe().getCheminPieceJointe());
        }

        commentaireRepository.deleteById(id);
    }

}
