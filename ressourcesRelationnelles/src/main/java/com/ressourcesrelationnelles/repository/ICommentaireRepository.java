package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentaireRepository extends JpaRepository<Commentaire,Integer> {
}
