package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.IUtilisateurService;
import com.ressourcesrelationnelles.service.UtilisateurService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citoyens/utilisateur")
@SecurityRequirement(name = "Authorization")
public class UtilisateurController {

    private final String port;

    private final String uri;
    @Autowired
    public UtilisateurController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private AuthService authService;

    @GetMapping
    public Utilisateur getMe(@RequestHeader("Authorization") String token){
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        return utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
    }

    @RequestMapping(value = "{id}",method= RequestMethod.PUT)
    public Utilisateur update(@PathVariable Integer id, @RequestParam("adresseMail") String adresseMail,@RequestParam("nom") String nom,@RequestParam("prenom") String prenom
                                ,@RequestParam("file") MultipartFile file,@RequestParam("deleteOldFile") Boolean deleteOldFile,
                                @RequestHeader("Authorization") String token) throws Exception {

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateurConnect = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));

        // On récupère l'objet de la BDD
        Utilisateur existingUtilisateur = utilisateurRepository.getReferenceById(id);
        if(utilisateurConnect.getId().equals(existingUtilisateur.getId()) || utilisateurConnect.getRole().getUserType().equals(UserType.ADMIN)){
            // On crée l'objet à modifier(nouvelles valeurs)
            Utilisateur utilisateur = utilisateurService.createFromForm(adresseMail,existingUtilisateur.getAdresseMail(), nom, prenom,file,uri,port);
            if(deleteOldFile){
               if(existingUtilisateur.getCheminPhotoProfil() != null && !existingUtilisateur.getCheminPhotoProfil().isEmpty()){
                   fileStorageService.deleteFileFromUrl(existingUtilisateur.getCheminPhotoProfil());
                   existingUtilisateur.setCheminPhotoProfil("");
               }
            }
            // Va copier les propriétés de l'objet utilisateur vers la variable existingUtilisateur en ignorant l'id afin de le conserver
            existingUtilisateur.setPrenom(utilisateur.getPrenom());
            existingUtilisateur.setNom(utilisateur.getNom());
            if(!file.isEmpty()){
                if(!deleteOldFile){
                    if(existingUtilisateur.getCheminPhotoProfil() != null && !existingUtilisateur.getCheminPhotoProfil().isEmpty()){
                        fileStorageService.deleteFileFromUrl(existingUtilisateur.getCheminPhotoProfil());
                        existingUtilisateur.setCheminPhotoProfil("");
                    }
                }
                existingUtilisateur.setCheminPhotoProfil(utilisateur.getCheminPhotoProfil());
            }
            existingUtilisateur.setAdresseMail(utilisateur.getAdresseMail());
            return utilisateurRepository.saveAndFlush(existingUtilisateur);
        }else{
            throw new Exception("Not allowed to edit this user");
        }
    }

    // TODO : remplace par une DTO et créer une fonction dans service pour faire plus propre
    @RequestMapping(value = "/update-password",method= RequestMethod.PUT)
    public Utilisateur updatePassword(@RequestParam("newPassword") String newPassword,@RequestParam("lastPassword") String lastPassword,
                              @RequestHeader("Authorization") String token) throws Exception {

        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateurConnect = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));

        BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
        if(encoder.matches(lastPassword,utilisateurConnect.getMotDePasse())){
            utilisateurConnect.setMotDePasse(encoder.encode(newPassword));
            utilisateurRepository.saveAndFlush(utilisateurConnect);
            return utilisateurConnect;
        }else{
            throw new Exception("Last password not match");
        }

    }

    //Suppression d'un utilisateur par id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id,@RequestHeader("Authorization") String token){
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(id);
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateurConnect = utilisateurRepository.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        utilisateurConnect.getRole().setRoleGranted();
        if(authService.IsAuthorize(utilisateur,utilisateurConnect, Arrays.asList(UserType.SUPER_ADMIN,UserType.ADMIN))){
            if (utilisateur.getCheminPhotoProfil() != null && !utilisateur.getCheminPhotoProfil().isEmpty()) {
                fileStorageService.deleteFileFromUrl(utilisateur.getCheminPhotoProfil());
            }
            utilisateurRepository.deleteById(id);
        }
    }

}
