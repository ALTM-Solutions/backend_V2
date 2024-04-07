package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.model.Message;
import com.ressourcesrelationnelles.repository.IMessageRepository;
import com.ressourcesrelationnelles.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/public/message")
public class MessageController {
    // TODO : Ici peut - être rajouté une suppression quand les message sont vieux de 1 jour,
    //  c'est du chat donc pas besoin de conserver ?
    private final String port;
    private final String uri;
    @Autowired
    private IMessageRepository messageRepository;
    @Autowired
    private MessageService messageService;

    @Autowired
    public MessageController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    // TODO : GET / READ par rapport à l'utilisateur/ressource pour n'obtenir que les messages sur cette ressource et envoyer par cet utilisateur.

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("message") String message, @RequestParam("envoyeur") Integer id_envoyeur, @RequestParam("recepteur") Integer id_recepteur, @RequestParam("idRessource") Integer id_ressource, @RequestParam("file") MultipartFile file) {
        Message m = messageService.createFromForm(message, id_envoyeur, id_recepteur, id_ressource, file, uri, port);
        messageRepository.saveAndFlush(m);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        // TODO : Suppression des pieceJointe / image / ressource
        messageRepository.deleteById(id);
    }

}
