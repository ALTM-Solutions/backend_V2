package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.FileStorageProperties;
import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.exception.FileStorageException;
import com.ressourcesrelationnelles.model.UploadFileResponse;
import com.ressourcesrelationnelles.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    private final String port;

    private final String uri;
    @Autowired
    public FileController(HostProperties hostProperties) {
        this.port = hostProperties.getPort();
        this.uri = hostProperties.getUri();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestPart("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        // TODO: Rendre flexible l'host pour mettre => http(s)://api-gateway_host:port_gateway/microservice-file/...


        System.out.println(uri + port);
        String fileDownloadUri = uri +":" +  port +"/api/public/file/downloadFile/" + fileName;

        return new UploadFileResponse(fileName, fileDownloadUri,
                fileStorageService.getFileType(fileName), file.getSize());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {

            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException ex) {

            logger.info("Could not determine file type.");

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/deleteFile/{fileName:.+}")
    public String deleteFile(@PathVariable String fileName){

        return  fileStorageService.deleteFile(fileName);

    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/findFile/{fileName:.+}")
    public String findFileExist(@PathVariable String fileName){
        return fileStorageService.findIfExist(fileName);
    }
}

