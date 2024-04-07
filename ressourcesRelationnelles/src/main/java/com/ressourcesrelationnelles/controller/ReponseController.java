package com.ressourcesrelationnelles.controller;


import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.model.Reponse;
import com.ressourcesrelationnelles.repository.IReponseRepository;
import com.ressourcesrelationnelles.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/public/reponse")
public class ReponseController {
    private final String port;
    private final String uri;
    @Autowired
    private IReponseRepository reponseRepository;
    @Autowired
    private ReponseService reponseService;

    @Autowired
    public ReponseController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("text") String text, @RequestParam("idCommentaire") Integer id_commentaire, @RequestParam("idUtilisateur") Integer id_utilisateur, @RequestParam("file") MultipartFile file) {
        Reponse reponse = reponseService.createFromForm(text, id_commentaire, id_utilisateur, file, uri, port);
        reponseRepository.saveAndFlush(reponse);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        reponseRepository.deleteById(id);
    }

}
