package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.CustomUserDetailsService;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.RegisterDto;
import com.ressourcesrelationnelles.dto.ResponseRegisterDto;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Route
@RequestMapping("api/super-admin/")
public class AuthSuperAdminController {

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

    @Autowired
    private AuthService authService;

    @GetMapping("all-user")
    public List<Utilisateur> getAll(){
        return userRepo.findAll();
    }

    @PostMapping("register-admin")
    public ResponseEntity<ResponseRegisterDto> registerAdmin(@RequestBody RegisterDto registerData){

        ResponseRegisterDto response = new ResponseRegisterDto();
        if(userRepo.existsByAdresseMail(registerData.getAdresseMail())){
            response.setMessage("Email is already registered !!");
            response.setSuccess(false);
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Utilisateur user = authService.createUser(registerData,UserType.ADMIN);
            response.setUsername(user.getAdresseMail());
            customUserDetailsService.setUserType(user.getRole().getUserType());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getAdresseMail(), registerData.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication,user.getRole().getUserType().toString());
            response.setId(user.getId());
            response.setToken(token);
            response.setSuccess(true);
            response.setRole(UserType.ADMIN.toString());
            response.setMessage("Profile created Successfully");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.CREATED);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Profile not created");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register-super-admin")
    public ResponseEntity<ResponseRegisterDto> registerSuperAdmin(@RequestBody RegisterDto registerData){

        ResponseRegisterDto response = new ResponseRegisterDto();
        if(userRepo.existsByAdresseMail(registerData.getAdresseMail())){
            response.setMessage("Email is already registered !!");
            response.setSuccess(false);
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Utilisateur user = authService.createUser(registerData,UserType.SUPER_ADMIN);
            response.setUsername(user.getAdresseMail());
            customUserDetailsService.setUserType(user.getRole().getUserType());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getAdresseMail(), registerData.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication,user.getRole().getUserType().toString());
            response.setId(user.getId());
            response.setToken(token);
            response.setSuccess(true);
            response.setRole(UserType.ADMIN.toString());
            response.setMessage("Profile created Successfully");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.CREATED);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Profile not created");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register-moderateur")
    public ResponseEntity<ResponseRegisterDto> registerModerateur(@RequestBody RegisterDto registerData){

        ResponseRegisterDto response = new ResponseRegisterDto();
        if(userRepo.existsByAdresseMail(registerData.getAdresseMail())){
            response.setMessage("Email is already registered !!");
            response.setSuccess(false);
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.BAD_REQUEST);
        }try {
            Utilisateur user = authService.createUser(registerData,UserType.MODERATEUR);
            response.setUsername(user.getAdresseMail());
            customUserDetailsService.setUserType(user.getRole().getUserType());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getAdresseMail(), registerData.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication,user.getRole().getUserType().toString());
            response.setId(user.getId());
            response.setToken(token);
            response.setSuccess(true);
            response.setRole(UserType.MODERATEUR.toString());
            response.setMessage("Profile created Successfully");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.CREATED);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Profile not created");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
