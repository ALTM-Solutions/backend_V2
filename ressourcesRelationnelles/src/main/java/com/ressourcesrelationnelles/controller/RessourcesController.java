package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RessourcesNotConnectedDto;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.FileStorageService;
import com.ressourcesrelationnelles.service.RessourcesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<RessourcesNotConnectedDto> getAll(){
        return ressourcesService.getAllRessourceNotConnected();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RessourcesNotConnectedDto getOne(@PathVariable Integer id){
        return ressourcesService.getOneRessourceNotConnected(id);
    }
}
