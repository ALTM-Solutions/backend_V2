package com.ressourcesrelationnelles.repository;

import com.ressourcesrelationnelles.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<Message,Integer> {
}
