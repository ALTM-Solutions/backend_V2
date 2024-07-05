package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.config.CustomUserDetailsService;
import com.ressourcesrelationnelles.config.JwtGenerator;
import com.ressourcesrelationnelles.config.SecurityConstants;
import com.ressourcesrelationnelles.dto.RegisterDto;
import com.ressourcesrelationnelles.model.Role;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.model.ValidationUtilisateur;
import com.ressourcesrelationnelles.repository.IRoleRepository;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import com.ressourcesrelationnelles.repository.IValidationUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    @Autowired
    private IUtilisateurRepository userRepo;

    @Autowired
    private IValidationUtilisateurRepository validationUtilisateurRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    public Boolean isSecuredPassword(String password){
        if (password.length() <= SecurityConstants.PASSWORD_SIZE) {
            return false;
        }
        // Vérifie si le mot de passe contient au moins une majuscule
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        // Vérifie si le mot de passe contient au moins un chiffre
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }
        // Vérifie si le mot de passe contient au moins un caractère spécial
        if (!Pattern.compile("[.@!?;,_-]").matcher(password).find()) {
            return false;
        }
        // Si toutes les conditions sont remplies, retourne true
        return true;
    }

    private static SecureRandom random = new SecureRandom();

    public Utilisateur validateUserToUser(ValidationUtilisateur validateUser, UserType userType)throws Exception{
        if(!userRepo.existsByAdresseMail(validateUser.getEmail())){
            Utilisateur user = new Utilisateur();
            user.setAdresseMail(validateUser.getEmail());
            Role citizenRole = roleRepository.findByNom(userType.toString()).get(0);
            user.setRole(citizenRole);
            // Hash le mdp
            user.setMotDePasse(validateUser.getPassword());
            user.setNom(validateUser.getNom());
            user.setPrenom(validateUser.getPrenom());
            user.setCheminPhotoProfil("");
            user.setDateDesactive(new Date(0));
            userRepo.saveAndFlush(user);
            user.getRole().setRoleGranted();
            return user;
        }else{
            throw new Exception("User exist");
        }
    }

    public Utilisateur getUserByToken(String token){
        String email = jwtGenerator.getUsernameFromJWT(token.substring(7));
        Utilisateur utilisateur = userRepo.findByAdresseMail(email).orElseThrow(()-> new UsernameNotFoundException("Username "+ email + "not found"));
        utilisateur.getRole().setRoleGranted();
        return utilisateur;
    }

    public void createValidateUserAndSendMail(RegisterDto registerData) throws Exception{
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(registerData.getAdresseMail());
        if(!matcher.matches()){
            throw new Exception("Adresse mail non valide");
        }
        if(!this.isSecuredPassword(registerData.getPassword())){
            throw new Exception("Mot de passe non valide");
        }
        try{
            // Vérification si un code existe déjà pour cette utilisateur
            if(validationUtilisateurRepository.existsByEmail(registerData.getAdresseMail())){
                validationUtilisateurRepository.delete(validationUtilisateurRepository.findByEmail(registerData.getAdresseMail()));
            }
            ValidationUtilisateur validationUtilisateur = new ValidationUtilisateur();
            validationUtilisateur.setEmail(registerData.getAdresseMail());
            validationUtilisateur.setPassword(passwordEncoder.encode(registerData.getPassword()));
            validationUtilisateur.setNom(registerData.getNom());
            validationUtilisateur.setPrenom(registerData.getPrenom());
            String code = generateCode();
            validationUtilisateur.setCode(code);
            // Création date expiration
            LocalDateTime date = LocalDateTime.now();
            LocalDateTime expire_local = date.plusMinutes(SecurityConstants.MINUTE_EXPIRE);
            Date expire = Date.from(expire_local.atZone(ZoneId.systemDefault()).toInstant());
            validationUtilisateur.setDateExpiration(expire);

            validationUtilisateurRepository.saveAndFlush(validationUtilisateur);

            emailService.sendEmailWithCode(validationUtilisateur.getEmail(), validationUtilisateur.getCode());

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    private String generateRandomPassword(){
        StringBuilder password = new StringBuilder(8);
        for (int i = 0; i < 8 - 4; i++) {
            password.append(PASSWORD_ALLOW_BASE.charAt(random.nextInt(PASSWORD_ALLOW_BASE.length())));
        }

        // Ensure the password has at least one character of each required type.
        password.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
        password.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
        password.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        password.append(OTHER_CHAR.charAt(random.nextInt(OTHER_CHAR.length())));

        return password.toString();
    }

    public void resetPassword(String email) throws Exception{
        if(userRepo.existsByAdresseMail(email)){
            String newPassword = generateRandomPassword();
            try{
                Utilisateur user = userRepo.findByAdresseMail(email).orElseThrow();
                user.setMotDePasse(passwordEncoder.encode(newPassword));
                userRepo.saveAndFlush(user);
                emailService.sendEmailWithNewPassword(email,newPassword);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }else{
            throw new Exception("no user with this email!");
        }
    }

    private String generateCode(){
        String code = UUID.randomUUID().toString();
        return code.substring(0,SecurityConstants.VALIDATION_CODE_SIZE).replaceAll("-","0");
    }

    public Utilisateur createUser(RegisterDto registerData, UserType userType) throws Exception {
        // Vérification inutile car se sont des utilisateur créer par le super-admin
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
                // Hash le mdp
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
