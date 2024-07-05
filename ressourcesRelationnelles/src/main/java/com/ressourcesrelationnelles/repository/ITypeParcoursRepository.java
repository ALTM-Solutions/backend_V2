package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.TypeParcours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeParcoursRepository extends JpaRepository<TypeParcours,Integer> {
    public TypeParcours findByNom(String nom);
    boolean existsByNom(String nom);
}
