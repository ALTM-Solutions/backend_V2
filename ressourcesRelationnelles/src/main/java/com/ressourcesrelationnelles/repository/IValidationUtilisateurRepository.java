package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.ValidationUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface IValidationUtilisateurRepository extends JpaRepository<ValidationUtilisateur, Integer> {
    ValidationUtilisateur findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    void deleteByDateExpirationLessThan(LocalDateTime dateTime);
}
