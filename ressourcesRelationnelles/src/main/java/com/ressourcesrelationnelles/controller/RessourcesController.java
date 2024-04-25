package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.model.*;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
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

import java.util.List;

@RestController
@RequestMapping("/api/public/ressources")
@SecurityRequirement(name = "Authorization")
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
}
