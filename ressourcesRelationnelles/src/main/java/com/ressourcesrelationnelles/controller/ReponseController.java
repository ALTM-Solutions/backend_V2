package com.ressourcesrelationnelles.controller;


import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.model.Reponse;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IReponseRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.ReponseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/citoyens/reponse")
@SecurityRequirement(name = "Authorization")
public class ReponseController {
    /*
       Ce controller permet d'ajouter/modifier/supprimer une réponse
    */
    @Autowired
    private IReponseRepository reponseRepository;

    @Autowired
    private ReponseService reponseService;

    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AuthService authService;
    @Autowired
    private IUtilisateurRepository utilisateurRepository;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Reponse create(@RequestParam("text") String text, @RequestParam("idCommentaire") Integer id_commentaire,
                       @RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file){
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        Reponse reponse = reponseService.createFromForm(text, id_commentaire, utilisateur.getId(),file);
        return reponseRepository.saveAndFlush(reponse);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id, @RequestHeader("Authorization") String token) throws Exception{
        Reponse reponse = reponseRepository.getReferenceById(id);

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        utilisateur.getRole().setRoleGranted();
        if(!authService.IsAuthorize(reponse.getUtilisateur(),utilisateur, Arrays.asList(UserType.SUPER_ADMIN,UserType.ADMIN, UserType.MODERATEUR))){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource");
        }
        if(reponse.getPieceJointe() != null){
            fileStorageService.deleteFileFromUrl(reponse.getPieceJointe().getCheminPieceJointe());
        }
        reponseRepository.deleteById(id);
    }

}
