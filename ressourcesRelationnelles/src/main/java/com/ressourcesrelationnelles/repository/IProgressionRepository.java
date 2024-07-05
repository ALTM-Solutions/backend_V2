package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Progression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgressionRepository extends JpaRepository<Progression, Integer> {

}
