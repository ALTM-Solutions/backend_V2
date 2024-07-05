package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.dto.CreatedRessourceDto;
import com.ressourcesrelationnelles.dto.RessourceConnectedDto;
import com.ressourcesrelationnelles.dto.RessourcesNotConnectedDto;
import com.ressourcesrelationnelles.model.Progression;
import com.ressourcesrelationnelles.model.Ressources;
import com.ressourcesrelationnelles.model.TypeParcours;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRessourcesRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RessourcesService implements IRessourcesService {
    @Autowired
    private IRessourcesRepository ressourcesRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private IUtilisateurRepository utilisateurRepository;



    private Progression getProgressUser(Ressources ressources,Utilisateur utilisateur){
        for(Progression progress : ressources.getProgressions()){
            if(progress.getUtilisateur().getId().equals(utilisateur.getId())){
                return progress;
            }
        }
        return null; // OU progession with 0%
    }

    public List<RessourceConnectedDto> getAllRessourcesConnected(Utilisateur utilisateur){

        List<RessourceConnectedDto> list = new ArrayList<>();

        for(Ressources res : ressourcesRepository.findAll()){
            if(res.isValide()){
                boolean isFavorite = false;
                boolean isMisDeCote = false;
                for(Utilisateur user : res.getUtilisateursFavorite()){
                    if(user.getId().equals(utilisateur.getId())){
                        isFavorite = true;
                    }
                }
                for(Utilisateur user : res.getUtilisateursMisDeCote()){
                    if(user.getId().equals(utilisateur.getId())){
                        isMisDeCote = true;
                    }
                }
                list.add(new RessourceConnectedDto(res,getProgressUser(res,utilisateur),res.getTypeParcours(),isFavorite,isMisDeCote));
            }
        }

        return list;

    }
    public List<RessourceConnectedDto> getAllRessourceFavorisUser(Utilisateur utilisateur){
        List<RessourceConnectedDto> list = new ArrayList<>();

        for(Ressources res : utilisateur.getFavorite()){
            if(res.isValide()){
                boolean isFavorite = false;
                boolean isMisDeCote = false;
                for(Utilisateur user : res.getUtilisateursFavorite()){
                    if(user.getId().equals(utilisateur.getId())){
                        isFavorite = true;
                    }
                }
                for(Utilisateur user : res.getUtilisateursMisDeCote()){
                    if(user.getId().equals(utilisateur.getId())){
                        isMisDeCote = true;
                    }
                }
                list.add(new RessourceConnectedDto(res,getProgressUser(res,utilisateur),res.getTypeParcours(),isFavorite,isMisDeCote));
            }
        }

        return list;
    }

    public List<RessourceConnectedDto> getAllRessourceMisDeCoteUser(Utilisateur utilisateur){
        List<RessourceConnectedDto> list = new ArrayList<>();

        for(Ressources res : utilisateur.getMisDeCote()){
            if(res.isValide()){
                boolean isFavorite = false;
                boolean isMisDeCote = false;
                for(Utilisateur user : res.getUtilisateursFavorite()){
                    if(user.getId().equals(utilisateur.getId())){
                        isFavorite = true;
                    }
                }
                for(Utilisateur user : res.getUtilisateursMisDeCote()){
                    if(user.getId().equals(utilisateur.getId())){
                        isMisDeCote = true;
                    }
                }
                list.add(new RessourceConnectedDto(res,getProgressUser(res,utilisateur),res.getTypeParcours(),isFavorite,isMisDeCote));
            }
        }

        return list;
    }

    public RessourceConnectedDto getOneRessourcesConnected(Utilisateur utilisateur, Integer ressourceId){
        Ressources ressources = ressourcesRepository.getReferenceById(ressourceId);
        if(ressources.isValide()){
            boolean isFavorite = false;
            boolean isMisDeCote = false;
            for(Utilisateur user : ressources.getUtilisateursFavorite()){
                if(user.getId().equals(utilisateur.getId())){
                    isFavorite = true;
                }
            }
            for(Utilisateur user : ressources.getUtilisateursMisDeCote()){
                if(user.getId().equals(utilisateur.getId())){
                    isMisDeCote = true;
                }
            }
            return new RessourceConnectedDto(ressources,getProgressUser(ressources,utilisateur),ressources.getTypeParcours(),isFavorite,isMisDeCote);
        }else{
            return null;
        }
    }


    public Ressources createFromForm(CreatedRessourceDto ressourceDto, MultipartFile file,List<TypeParcours> listTypeParcours){
        Ressources ressources = new Ressources();
        ressources.setNomRessource(ressourceDto.getNomRessource());
        if(!file.isEmpty()) {
            ressources.setPieceJointe(fileStorageService.createPieceJointe(file));
        }else{
            ressources.setPieceJointe(null);
        }
        ressources.setText(ressourceDto.getTexte());
        ressources.setUtilisateur(utilisateurRepository.getReferenceById(ressourceDto.getIdUtilisateur()));
        ressources.setDatePublication(new Date());
        ressources.setDateModification(new Date());
        ressources.setValide(false);
        ressources.setTypeParcours(listTypeParcours);
        return ressources;
    }

    public Ressources createFromForm(CreatedRessourceDto ressourceDto){
        Ressources ressources = new Ressources();
        ressources.setNomRessource(ressourceDto.getNomRessource());
        ressources.setText(ressourceDto.getTexte());
        ressources.setUtilisateur(utilisateurRepository.getReferenceById(ressourceDto.getIdUtilisateur()));
        ressources.setDatePublication(new Date());
        ressources.setDateModification(new Date());
        return ressources;
    }


    public void unvalidedRessource(Integer ressourceId){

        Ressources ressources = ressourcesRepository.getReferenceById(ressourceId);
        ressources.setValide(false);
        ressourcesRepository.saveAndFlush(ressources);

    }

    public void validedRessource(Integer ressourceId){

        Ressources ressources = ressourcesRepository.getReferenceById(ressourceId);
        ressources.setValide(true);
        ressourcesRepository.saveAndFlush(ressources);

    }

    public List<Ressources> getAllRessourceNoFilter(){
        return ressourcesRepository.findAll();
    }

    public Ressources getRessourceNoFilter(Integer id){
        return ressourcesRepository.getReferenceById(id);
    }

    public List<RessourcesNotConnectedDto> getAllRessourceNotConnected(){
        List<RessourcesNotConnectedDto> list = new ArrayList<>();
        for(Ressources res : ressourcesRepository.findAll()){
            if(res.isValide()) {
                list.add(new RessourcesNotConnectedDto(res, res.getTypeParcours()));
            }

        }
        return list;
    }

    public RessourcesNotConnectedDto getOneRessourceNotConnected(Integer id){
        Ressources ressources = ressourcesRepository.getReferenceById(id);
        if(ressources.isValide()){
            return new RessourcesNotConnectedDto(ressources,ressources.getTypeParcours());
        }else{
            return null;
        }
    }

    public Ressources createRessources(Utilisateur utilisateur, String texte, String nomRessource, MultipartFile file, List<TypeParcours> listTypeParcours){

        CreatedRessourceDto ressourceDto = new CreatedRessourceDto();
        ressourceDto.setTexte(texte);
        ressourceDto.setNomRessource(nomRessource);
        ressourceDto.setIdUtilisateur(utilisateur.getId());

        return ressourcesRepository.saveAndFlush(this.createFromForm(ressourceDto,file, listTypeParcours));

    }


}
