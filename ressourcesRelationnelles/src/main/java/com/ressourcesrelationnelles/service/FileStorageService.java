package com.ressourcesrelationnelles.service;


import com.ressourcesrelationnelles.config.FileStorageProperties;
import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.exception.FileStorageException;
import com.ressourcesrelationnelles.exception.MyFileNotFoundException;
import com.ressourcesrelationnelles.model.PieceJointe;
import com.ressourcesrelationnelles.repository.IPieceJointeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private IPieceJointeRepository pieceJointeRepository;
    private final Path fileStorageLocation;


    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public PieceJointe createPieceJointe(MultipartFile file,String uri,String port){

        String filePath = this.storeFile(file);
        PieceJointe pieceJointe = new PieceJointe();
        // Infos récupérées dans le application.properties grâce au "FileStorageProperties" qui est dans le package config
        String fileDownloadUri = uri +":" +  port +"/api/public/file/downloadFile/" + filePath;
        pieceJointe.setCheminPieceJointe(fileDownloadUri);
        return pieceJointeRepository.saveAndFlush(pieceJointe);

    }
    public String getFileType(String filename){
        try {
            String[] elements = filename.split("\\.");
            String extension = elements[elements.length - 1];

            switch (extension) {
                case "doc", "docx", "txt", "pdf" -> {
                    // Fichier texte
                    if (extension.equals("doc") || extension.equals("docx")) {
                        return "application/msword";
                    } else if (extension.equals("txt")) {
                        return "text/plain";
                    } else {
                        return "application/pdf";
                    }
                }
                case "jpg", "jpeg", "png", "gif", "bmp", "svg", "tif" -> {
                    // Fichier image
                    return "image/" + extension;
                }
                case "mp3", "wav", "aac", "flac" -> {
                    // Fichier audio
                    if (extension.equals("mp3")) {
                        return "audio/mpeg";
                    }
                    return "audio/" + extension;
                }
                case "mp4", "avi", "flv", "mov" -> {
                    // Fichier Vidéo
                    extension = switch (extension) {
                        case "avi" -> "x-msvideo";
                        case "flv" -> "x-flv";
                        default -> "x-quicktime";
                    };
                    return "video/" + extension;
                }
                case "html", "css", "js" -> {
                    // Fichier Web
                    if (extension.equals("js")) {
                        return "application/javascript";
                    } else {
                        return "text/" + extension;
                    }
                }
                // Fichier Dev
                case "zip", "rar", "7z", "gz" -> {
                    // Fichier Archive
                    if (extension.equals("rar") || extension.equals("7z")) {
                        return "application/x-" + extension + "-compressed";
                    } else if (extension.equals("gz")) {
                        return "application/gzip";
                    } else {
                        return "application/" + extension;
                    }
                }
                case "exe", "jar", "apk" -> {
                    // Fichier executable
                    return switch (extension) {
                        case "exe" -> "application/x-msdownload";
                        case "jar" -> "application/java-archive";
                        case "apk" -> "application/vnd.android.package.archive";
                        default -> "application/octet-stream";
                    };
                }
                default -> {
                    return "application/octet-stream";
                }
            }
        }catch (Exception e){
            return "application/octet-stream";
        }
    }
    public String storeFile(MultipartFile file) {

        try {
            // Normalize file name
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String[] elements = fileName.split("\\.");
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < elements.length - 1; i++) {
                sb.append(elements[i]);
                if (i != elements.length - 2) {
                    sb.append(" ");
                }
            }
            String extension = elements[elements.length -1];

            // rename file to get unique name
            String fileWithoutExtension = UUID.randomUUID().toString();
            fileName = fileWithoutExtension + "." + extension;
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            if(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("tif") || extension.equals("bmp")){
                ImageIO.write(ImageIO.read(new File(targetLocation.toFile().toString())),"webp",new File(targetLocation.getParent().toString() + "/" + fileWithoutExtension + ".webp"));
                Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
                Files.deleteIfExists(filePath);
                fileName = fileWithoutExtension + ".webp";
            }

            return fileName;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file. Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public String deleteFile(String fileName){

        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

        try {
            String rootPath = filePath.getParent().toString();

            if(filePath.getFileName().toString().contains(".")) {

                if(Files.deleteIfExists(filePath)){

                    return fileName + ", deleted.";

                }else{

                    throw new FileNotFoundException("file : " + fileName + ", not found.");

                }

            }else {
                // to delete all file with any extension juste past name file without extension
                String nameFile = fileName;
                List<Path> result = new ArrayList<>();

                String finalNameFile = nameFile;
                try (Stream<Path> pathStream = Files.find(Path.of(rootPath), 1,
                        (p, basicFileAttributes) -> p.getFileName().toString().split("\\.")[0].equals(finalNameFile))) {

                    result = pathStream.collect(Collectors.toList());

                }catch (Exception e){

                    throw new FileStorageException("Sorry, an error was occured on delete file name : " + fileName);

                }

                if(!result.isEmpty()) {
                    for (Path path : result) {

                        Files.deleteIfExists(path);

                    }
                    return nameFile + " deleted.";
                }else{
                    throw new FileNotFoundException("files : " + nameFile + ", not found.");
                }
            }
        }catch (IOException e){

            throw new FileStorageException("Sorry, an error was occured on delete file name : " + fileName);

        }
    }

    public void deleteFileFromUrl(String path){
        String[] elements = path.split("/");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elements.length - 1; i++) {
            sb.append(elements[i]);
            if (i != elements.length - 2) {
                sb.append(" ");
            }
        }
        String fileName = elements[elements.length -1];
        this.deleteFile(fileName);
    }

    public String  findIfExist(String fileName){

        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        String rootPath = filePath.getParent().toString();

        if(filePath.getFileName().toString().contains(".")) {

            if(Files.exists(filePath)){

                return fileName + ", found.";

            }else{

                return fileName + ", not found.";

            }

        }else {
            // to delete all file with any extension juste past name file without extension
            String nameFile = fileName;
            List<Path> result = new ArrayList<>();

            String finalNameFile = nameFile;
            try (Stream<Path> pathStream = Files.find(Path.of(rootPath), 1,
                    (p, basicFileAttributes) -> p.getFileName().toString().split("\\.")[0].equals(finalNameFile))) {

                result = pathStream.collect(Collectors.toList());

            }catch (Exception e){

                throw new FileStorageException("Sorry, an error was occured on delete file name : " + fileName);

            }

            if(!result.isEmpty()) {
                String output = "";
                for(int i=0;i<result.size();i++){
                    if(i == result.size() - 1){
                        output += result.get(i).getFileName();
                    }else{
                        output += result.get(i).getFileName() + ", ";
                    }


                }
                return output  + ", found.";
            }else{
                return fileName + ", not found.";
            }
        }

    }
}