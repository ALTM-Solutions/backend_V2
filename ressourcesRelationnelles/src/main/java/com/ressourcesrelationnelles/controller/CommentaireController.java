package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.model.Commentaire;
import com.ressourcesrelationnelles.repository.ICommentaireRepository;
import com.ressourcesrelationnelles.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/public/commentaire")
public class CommentaireController {
    // TODO : Supprimer le "idUtilisateur" il doit venir du Token
    private final String port;
    private final String uri;
    @Autowired
    private ICommentaireRepository commentaireRepository;
    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    public CommentaireController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("contenu") String contenu, @RequestParam("idRessource") Integer id_ressource,
                       @RequestParam("idUtilisateur") Integer id_utilisateur, @RequestParam("file") MultipartFile file) {

        Commentaire commentaire = commentaireService.createFromJson(contenu, id_ressource, id_utilisateur, file, uri, port);
        commentaireRepository.saveAndFlush(commentaire);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        commentaireRepository.deleteById(id);
    }

}
