package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.service.FileStorageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
// @RestController : Rassemble 2 annotations @Controller et @ResponseBody. Défini à Spring que toutes les méthodes renvoient une réponse au client
@RestController
@RequestMapping("/api/public/file")
public class FileController {

    /*
        Ce controller permet le téléchargement des fichiers de ressources
     */

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Charger un fichier en tant que ressource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String filename = fileStorageService.getOrigineNameIfExist(fileName);
        // Essayer de déterminer le type de contenu du fichier
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        // Retour au type de contenu par défaut si le type n'a pas pu être déterminé
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}

