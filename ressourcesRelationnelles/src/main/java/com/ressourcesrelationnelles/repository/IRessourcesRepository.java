package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Ressources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRessourcesRepository extends JpaRepository<Ressources, Integer> {

}
