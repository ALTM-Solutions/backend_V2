package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.*;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/citoyens/ressources")
@SecurityRequirement(name = "Authorization")
public class RessourcesConnectedController {
    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private RessourcesService ressourcesService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IPieceJointeRepository pieceJointeRepository;

    @Autowired
    private AuthService authService;
    private final String port;

    private final String uri;
    @Autowired
    public RessourcesConnectedController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    // Récupérer toutes les ressources
    @GetMapping
    public List<Ressources> getAll(){
        return ressourcesRepository.findAll();
    }

    // Récupérer une ressource avec l'id
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Ressources getOne(@PathVariable Integer id){
        return ressourcesRepository.getReferenceById(id);
    }

    // Création d'une ressource
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Ressources create(@RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token , @RequestParam("file")MultipartFile file){
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));;
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(utilisateur.getId());
        return ressourcesRepository.saveAndFlush(ressourcesService.createFromForm(ressourceDto,file,uri,port));
    }

    // Modification d'une ressource (nouveau texte et nouveau nom)
    @RequestMapping(value = "/text/{id}",method= RequestMethod.PUT)
    public Ressources updateText(@PathVariable Integer id, @RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token)throws Exception{
        Ressources existingRessources = ressourcesRepository.getReferenceById(id); // Récupère la ressource grâce à l'id
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7)); // Obtient le mail de l'utilisateur grâce au JWT
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found")); // Récupère l'utilisateur par son email
        if(!authService.IsAuthorize(existingRessources.getUtilisateur(),utilisateur, Arrays.asList(UserType.SUPER_ADMIN,UserType.ADMIN, UserType.MODERATEUR))){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource"); // Vérifie si l'utilisateur a le droit de modifier la ressource
        }
        // Crée un nouveau DTO pour la ressource
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte); // Définit le texte de la ressource
        ressourceDto.setNomRessource(nomRessource); // Définit le nom de la ressource
        ressourceDto.setIdUtilisateur(utilisateur.getId()); // Définit l'ID de l'utilisateur
        Ressources ressources = ressourcesService.createFromForm(ressourceDto); // Crée une nouvelle ressource à partir du DTO
        ressources.setPieceJointe(existingRessources.getPieceJointe()); // Copie la pièce jointe de la ressource existante
        BeanUtils.copyProperties(ressources,existingRessources,"id"); // Copie les propriétés de la nouvelle ressource dans la ressource existante
        return ressourcesRepository.saveAndFlush(existingRessources); // Enregistre et renvoie la ressource mise à jour
    }

    // Modification d'une ressource (même chose mais avec la MAJ du fichier)
    @RequestMapping(value = "/text-and-file/{id}",method= RequestMethod.PUT)
    public Ressources updateWithFile(@PathVariable Integer id, @RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token,@RequestParam("file")MultipartFile file) throws Exception{
        Ressources existingRessources = ressourcesRepository.getReferenceById(id); // Récupère la ressource existante
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7)); // Email utilisateur à partir du JWT
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found")); // Récupération de l'user par son mail
        if(!utilisateur.getId().equals(existingRessources.getUtilisateur().getId())){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource"); // Vérifie si l'utilisateur a le droit de modifier la ressource
        }
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto(); // Crée un nouveau DTO pour la ressource
        ressourceDto.setTexte(texte); // Définit le texte de la ressource
        ressourceDto.setNomRessource(nomRessource); // Définit le nom de la ressource
        ressourceDto.setIdUtilisateur(utilisateur.getId());  // Définit l'ID de l'utilisateur
        Ressources ressources = ressourcesService.createFromForm(ressourceDto,file,uri,port); // Crée une nouvelle ressource à partir du DTO et du fichier
        if(existingRessources.getPieceJointe() != null){
            // TODO : Voir comment on gère les exceptions de tentative de suppression je pense que on skip juste
            // Si la ressource existante a une pièce jointe, supprime la pièce jointe existante
            try{
                fileStorageService.deleteFileFromUrl(existingRessources.getPieceJointe().getCheminPieceJointe()); // Supprime le fichier de la pièce jointe existante
            }catch (Exception e){
                System.out.println(e.getMessage()); // Imprime le message d'erreur si la suppression échoue
            }
            PieceJointe pieceJointe = existingRessources.getPieceJointe(); // Récupère la pièce jointe existante
            existingRessources.setPieceJointe(null); // Supprime la référence à la pièce jointe dans la ressource existante
            ressourcesRepository.saveAndFlush(existingRessources);  // Enregistre la ressource existante sans la pièce jointe
            pieceJointeRepository.delete(pieceJointe); // Supprime la pièce jointe de la base de données
        }
        BeanUtils.copyProperties(ressources,existingRessources,"id"); // Copie les propriétés de la nouvelle ressource dans la ressource existante
        return ressourcesRepository.saveAndFlush(existingRessources); // Enregistre et renvoie la ressource mise à jour
    }

    // Suppression d'une ressource
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id, @RequestHeader("Authorization") String token) throws Exception{
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        Ressources existingRessources = ressourcesRepository.getReferenceById(id);
        utilisateur.getRole().setRoleGranted();
        if(!authService.IsAuthorize(existingRessources.getUtilisateur(),utilisateur, Arrays.asList(UserType.SUPER_ADMIN,UserType.ADMIN, UserType.MODERATEUR))){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource");
        }
        for(Commentaire commentaire:existingRessources.getCommentaires()){
            for (Reponse reponse :commentaire.getReponses()) {
                if(reponse.getPieceJointe() != null) {
                    fileStorageService.deleteFileFromUrl(reponse.getPieceJointe().getCheminPieceJointe());
                }
            }
            if(commentaire.getPieceJointe() != null) {
                fileStorageService.deleteFileFromUrl(commentaire.getPieceJointe().getCheminPieceJointe());
            }
        }
        for(Message message:existingRessources.getMessages()){
            if(message.getPieceJointe() != null) {
                fileStorageService.deleteFileFromUrl(message.getPieceJointe().getCheminPieceJointe());
            }
        }
        if(existingRessources.getPieceJointe() != null) {
            fileStorageService.deleteFileFromUrl(existingRessources.getPieceJointe().getCheminPieceJointe());
        }
        ressourcesRepository.deleteById(id);
    }
}
