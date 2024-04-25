package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.model.Message;
import com.ressourcesrelationnelles.repository.IMessageRepository;
import com.ressourcesrelationnelles.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/citoyens/message")
@SecurityRequirement(name = "Authorization")
public class MessageController {
    // TODO : Ici peut - être rajouter une suppression quand les message sont vieux de 1 jour,
    //  c'est du chat donc pas besoinn de conservé ?

    private final String port;

    private final String uri;
    @Autowired
    public MessageController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    // TODO : GET / READ par rapport à l'utilisateur/ressource pour n'btenir que les message sur cette ressource et envoyé par cette utilisateur.

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestParam("message") String message, @RequestParam("envoyeur") Integer id_envoyeur, @RequestParam("recepteur") Integer id_recepteur, @RequestParam("idRessource")Integer id_ressource, @RequestParam("file")MultipartFile file){

        Message m = messageService.createFromForm(message,id_envoyeur,id_recepteur,id_ressource,file,uri,port);
        messageRepository.saveAndFlush(m);

    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id){
        // TODO : Suppression des pieceJointe / image / ressource
        messageRepository.deleteById(id);
    }


}
