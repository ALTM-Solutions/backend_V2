package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;
    public void createIfNotExist(UserType userType){

        if(!roleRepository.existsByNom(userType.toString())){
            Role role = new Role(userType.toString());
            roleRepository.saveAndFlush(role);
        }
    }


    public void createAllUserType(){
        for(UserType type : UserType.values()){
            createIfNotExist(type);
        }
    }
}
