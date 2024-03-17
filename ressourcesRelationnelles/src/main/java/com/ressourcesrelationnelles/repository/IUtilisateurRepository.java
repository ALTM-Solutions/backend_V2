package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByAdresseMail(String adresseMail);


    boolean existsByAdresseMail(String adresseMail);
}
