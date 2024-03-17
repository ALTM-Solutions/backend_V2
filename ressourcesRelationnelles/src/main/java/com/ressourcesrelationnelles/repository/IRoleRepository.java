package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    //Spring Data JPA va automatiquement générer la fonction findByName()
    List<Role> findByNom(String name);
    boolean existsByNom(String nom);
}
