package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // Lève une exception si il n'y a pas d'utilisateur, doit donc être implémenté avec "..orElseThrow(() -> new UsernameNotFoundException("Username " + adresseMail + "not found"));"
    Optional<Utilisateur> findByAdresseMail(String adresseMail);

    boolean existsByAdresseMail(String adresseMail);
}
