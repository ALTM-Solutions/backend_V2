package com.ressourcesrelationnelles.controller;

import com.ressourcesrelationnelles.config.CustomUserDetailsService;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.dto.LoginDto;
import com.ressourcesrelationnelles.dto.RegisterDto;
import com.ressourcesrelationnelles.dto.ResponseLoginDto;
import com.ressourcesrelationnelles.dto.ResponseRegisterDto;
import com.ressourcesrelationnelles.model.Role;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
// Route
@RequestMapping("api/public/")
public class AuthController {
    // Permet de faire de l'injection de dépendances => simplifie le code, le rend + fiable et + facile à tester
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

    @PostMapping("login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody LoginDto loginDTO) {
        if(userRepo.existsByAdresseMail(loginDTO.getEmail())){

            Utilisateur user = userRepo.findByAdresseMail(loginDTO.getEmail()).orElseThrow();
            Date now = new Date();
            if(user.getDateDesactive().before(now)){
                user.getRole().setRoleGranted();
                UserType userType = user.getRole().getUserType();

                customUserDetailsService.setUserType(userType);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = jwtGenerator.generateToken(authentication,userType.toString());
                ResponseLoginDto responseDto = new ResponseLoginDto();
                responseDto.setSuccess(true);
                responseDto.setMessage("login successful !!");
                responseDto.setToken(token);
                responseDto.setDetails(user.getAdresseMail(), user.getId(), user.getRole().getNom());
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }else{
                ResponseLoginDto responseDto = new ResponseLoginDto();
                responseDto.setSuccess(false);
                responseDto.setMessage("user disabled");
                return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
            }

        }else{
            ResponseLoginDto responseDto = new ResponseLoginDto();
            responseDto.setSuccess(false);
            responseDto.setMessage("User not found!");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("register")
    public ResponseEntity<ResponseRegisterDto> register(@RequestBody RegisterDto registerData){

        ResponseRegisterDto response = new ResponseRegisterDto();
        if(userRepo.existsByAdresseMail(registerData.getAdresseMail())){
            response.setMessage("Email is already registered !!");
            response.setSuccess(false);
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Utilisateur user = authService.createUser(registerData,UserType.CITIZEN);

            response.setUsername(user.getAdresseMail());
            // generate token
            user.getRole().setRoleGranted();
            customUserDetailsService.setUserType(user.getRole().getUserType());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getAdresseMail(), registerData.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication,user.getRole().getUserType().toString());
            response.setId(user.getId());
            response.setToken(token);
            response.setSuccess(true);
            response.setRole(user.getRole().getNom());
            response.setMessage("Profile created Successfully");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.CREATED);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Profile not created");
            return new ResponseEntity<ResponseRegisterDto>(response,HttpStatus.CREATED);
        }
    }

}