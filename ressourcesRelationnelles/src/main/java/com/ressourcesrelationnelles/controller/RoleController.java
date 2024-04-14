package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/role")
public class RoleController {
    @Autowired
    private IRoleRepository roleRepository;

    @GetMapping
    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //renvoi que c'est crée = 201
    public void create(@RequestBody final Role role){
        roleRepository.saveAndFlush(role);
    }
}
