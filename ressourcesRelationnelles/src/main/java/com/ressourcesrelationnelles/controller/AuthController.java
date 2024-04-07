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

@RestController
@RequestMapping("api/v1/")
public class AuthController {
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

    @PostMapping("login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody LoginDto loginDTO) {
        if (userRepo.existsByAdresseMail(loginDTO.getEmail())) {

            Utilisateur user = userRepo.findByAdresseMail(loginDTO.getEmail()).orElseThrow();
            user.getRole().setRoleGranted();
            UserType userType = user.getRole().getUserType();

            customUserDetailsService.setUserType(userType);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication, userType.toString());
            ResponseLoginDto responseDto = new ResponseLoginDto();
            responseDto.setSuccess(true);
            responseDto.setMessage("login successful !!");
            responseDto.setToken(token);
            responseDto.setDetails(user.getAdresseMail(), user.getId());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            ResponseLoginDto responseDto = new ResponseLoginDto();
            responseDto.setSuccess(false);
            responseDto.setMessage("User not found!");
            return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("register")
    public ResponseEntity<ResponseRegisterDto> register(@RequestBody RegisterDto registerData) {

        ResponseRegisterDto response = new ResponseRegisterDto();
        if (userRepo.existsByAdresseMail(registerData.getAdresseMail())) {
            response.setMessage("Email is already registered !!");
            response.setSuccess(false);
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.BAD_REQUEST);
        }
        Utilisateur user = new Utilisateur();
        user.setAdresseMail(registerData.getAdresseMail());
        try {
            Role citizenRole = roleRepository.findByNom(UserType.CITIZEN.toString()).get(0);
            user.setRole(citizenRole);
            user.setMotDePasse(passwordEncoder.encode(registerData.getPassword()));
            user.setNom(registerData.getNom());
            user.setPrenom(registerData.getPrenom());
            user.setCheminPhotoProfil(registerData.getCheminPhotoProfil());
            userRepo.saveAndFlush(user);
            response.setSuccess(true);
            response.setMessage("Profile created Successfully");
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.CREATED);
        } catch (IndexOutOfBoundsException e) {
            response.setSuccess(false);
            response.setMessage("Profile not created");
            return new ResponseEntity<ResponseRegisterDto>(response, HttpStatus.CREATED);
        }

    }
}