package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.UtilisateurService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/utilisateur")
public class UtilisateurController {
    @Autowired
    private IUtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping()
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    //Création d'un utilisateur qui est géré dans le service de l'utilisateur
    //permet de garder un Controller propre, chacun a son rôle :)
    @PostMapping
    public void create(@RequestBody final Map<String, Object> json_utilisateur) {
        Utilisateur utilisateur = utilisateurService.createFromJson(json_utilisateur);
        utilisateurRepository.saveAndFlush(utilisateur);
    }

    //MAJ d'un utilisateur
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Utilisateur update(@PathVariable Integer id, @RequestBody final Map<String, Object> json_utilisateur) {
        //On récupère l'objet de la BDD
        Utilisateur existingUtilisateur = utilisateurRepository.getReferenceById(id);
        //On crée l'objet à modifier(nouvelles valeurs)
        Utilisateur utilisateur = utilisateurService.createFromJson(json_utilisateur);
        //Va copier les propriétés de l'objet utilisateur vers la variable existingUtilisateur en ignorant l'id afin de le conserver
        BeanUtils.copyProperties(utilisateur, existingUtilisateur, "id");
        //Puis sauvegarde en BDD
        return utilisateurRepository.saveAndFlush(existingUtilisateur);
    }

    //Suppression d'un utilisateur par id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        utilisateurRepository.deleteById(id);
    }
}
