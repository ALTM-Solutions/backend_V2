package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.*;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.IRessourcesService;
import com.ressourcesrelationnelles.service.RessourcesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/ressources")
public class RessourcesController {
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


    private final String port;

    private final String uri;
    @Autowired
    public RessourcesController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @GetMapping
    public List<Ressources> getAll(){
        return ressourcesRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Ressources getOne(@PathVariable Integer id){

        return ressourcesRepository.getReferenceById(id);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token , @RequestParam("file")MultipartFile file){

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));;
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(utilisateur.getId());
        ressourcesRepository.saveAndFlush(ressourcesService.createFromForm(ressourceDto,file,uri,port));
    }

    @RequestMapping(value = "/text/{id}",method= RequestMethod.PUT)
    public Ressources updateText(@PathVariable Integer id, @RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token)throws Exception{

        Ressources existingRessources = ressourcesRepository.getReferenceById(id);

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        if(!utilisateur.getId().equals(existingRessources.getUtilisateur().getId())){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource");
        }
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(utilisateur.getId());
        Ressources ressources = ressourcesService.createFromForm(ressourceDto);
        ressources.setPieceJointe(existingRessources.getPieceJointe());

        BeanUtils.copyProperties(ressources,existingRessources,"id");
        return ressourcesRepository.saveAndFlush(existingRessources);
    }

    @RequestMapping(value = "/text-and-file/{id}",method= RequestMethod.PUT)
    public Ressources updateWithFile(@PathVariable Integer id, @RequestParam("texte") String texte,@RequestParam("nomRessource") String nomRessource, @RequestHeader("Authorization") String token,@RequestParam("file")MultipartFile file) throws Exception{

        Ressources existingRessources = ressourcesRepository.getReferenceById(id);

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        if(!utilisateur.getId().equals(existingRessources.getUtilisateur().getId())){
            throw new Exception("l'utilisateur n'a pas le droit de modifier cette ressource");
        }
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(utilisateur.getId());
        Ressources ressources = ressourcesService.createFromForm(ressourceDto,file,uri,port);
        if(existingRessources.getPieceJointe() != null){
            // TODO : Voir comment on gére les exceptions de tentative de suppression je pense que on skip juste
            try{
                fileStorageService.deleteFileFromUrl(existingRessources.getPieceJointe().getCheminPieceJointe());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            PieceJointe pieceJointe = existingRessources.getPieceJointe();
            existingRessources.setPieceJointe(null);
            ressourcesRepository.saveAndFlush(existingRessources);
            pieceJointeRepository.delete(pieceJointe);
        }

        BeanUtils.copyProperties(ressources,existingRessources,"id");
        return ressourcesRepository.saveAndFlush(existingRessources);
    }



    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id, @RequestHeader("Authorization") String token) throws Exception{

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        Ressources existingRessources = ressourcesRepository.getReferenceById(id);

        if(!utilisateur.getId().equals(existingRessources.getUtilisateur().getId())){
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
