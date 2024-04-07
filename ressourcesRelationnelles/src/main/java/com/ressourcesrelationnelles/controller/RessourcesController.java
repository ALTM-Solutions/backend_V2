package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.service.RessourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/ressources")
public class RessourcesController {
    private final String port;
    private final String uri;
    @Autowired
    private IRessourcesRepository ressourcesRepository;
    @Autowired
    private RessourcesService ressourcesService;

    @Autowired
    public RessourcesController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @GetMapping
    public List<Ressources> getAll() {
        return ressourcesRepository.findAll();
    }

    // TODO : getOne

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("texte") String texte, @RequestParam("nomRessource") String nomRessource, @RequestParam("idUtilisateur") Integer idUtilisateur, @RequestParam("file") MultipartFile file) {
        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(idUtilisateur);
        ressourcesService.createFromForm(ressourceDto, file, uri, port);
    }

    // TODO Update

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        ressourcesRepository.deleteById(id);
    }
}
