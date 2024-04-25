package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
// Route
@RequestMapping("api/admin/utilisateur")
public class AdminUtilisateurController {

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

    // Récupère la liste de tous les users
    @GetMapping("all-user")
    public List<Utilisateur> getAll(){
        return utilisateurRepository.findAll();
    }

    // Permet de désactiver un utilisateur pendant X jours
    @RequestMapping(value = "disable/{id}", method = RequestMethod.POST)
    public void disable(@PathVariable Integer id,@RequestParam("nbJour") Integer nbJour) throws Exception{
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(id);
        utilisateur.getRole().setRoleGranted();
        if(!utilisateur.getRole().getUserType().equals(UserType.CITIZEN)){
            throw new Exception("Seul le compte citoyen peut être désactivé");
        }
        LocalDate date = LocalDate.now();
        Date now = new Date();
        LocalDate desactivate = date.plusDays(nbJour);
        java.sql.Date sqlDate = java.sql.Date.valueOf(desactivate);
        Date test = new Date(sqlDate.getTime());
        utilisateur.setDateDesactive(test);
        utilisateurRepository.saveAndFlush(utilisateur);
    }

    // Réactive un utilisateur en définissant sa date de desactivation à 1970, 00:00:00 GMT.
    @RequestMapping(value = "enable/{id}", method = RequestMethod.POST)
    public void enable(@PathVariable Integer id) throws Exception{
        Utilisateur utilisateur = utilisateurRepository.getReferenceById(id);
        utilisateur.getRole().setRoleGranted();
        if(!utilisateur.getRole().getUserType().equals(UserType.CITIZEN)){
            throw new Exception("Seul le compte citoyen peut être désactivé");
        }
        Date firstDay = new Date(0);
        utilisateur.setDateDesactive(firstDay);
        utilisateurRepository.saveAndFlush(utilisateur);
    }

}
