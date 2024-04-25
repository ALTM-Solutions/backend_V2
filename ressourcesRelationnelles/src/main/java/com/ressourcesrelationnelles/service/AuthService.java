package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.config.CustomUserDetailsService;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RegisterDto;
import com.ressourcesrelationnelles.dto.ResponseRegisterDto;
import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private IUtilisateurRepository userRepo;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    public Utilisateur createUser(RegisterDto registerData, UserType userType) throws Exception {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(registerData.getAdresseMail());
        if(!matcher.matches()){
            throw new Exception("Adresse mail non valide");
        }
        try {
            if(!userRepo.existsByAdresseMail(registerData.getAdresseMail())){
                Utilisateur user = new Utilisateur();
                user.setAdresseMail(registerData.getAdresseMail());
                Role citizenRole = roleRepository.findByNom(userType.toString()).get(0);
                user.setRole(citizenRole);
                user.setMotDePasse(passwordEncoder.encode(registerData.getPassword()));
                user.setNom(registerData.getNom());
                user.setPrenom(registerData.getPrenom());
                user.setCheminPhotoProfil("");
                user.setDateDesactive(new Date(0));
                userRepo.saveAndFlush(user);
                user.getRole().setRoleGranted();
                return user;
            }else{
                throw new Exception("User exist");
            }


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean IsAuthorize(Utilisateur user,Utilisateur userConnected, List<UserType> listRoleAccess){
        return userConnected.getId().equals(user.getId()) || listRoleAccess.contains(userConnected.getRole().getUserType());
    }


}
