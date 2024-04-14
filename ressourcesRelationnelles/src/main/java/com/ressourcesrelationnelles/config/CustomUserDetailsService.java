package com.ressourcesrelationnelles.config;

import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.model.Utilisateur;
import com.ressourcesrelationnelles.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUtilisateurRepository userRepo;
    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = userRepo.findByAdresseMail(username).orElseThrow(()-> new UsernameNotFoundException("Username "+ username+ "not found"));
        user.getRole().setRoleGranted();
        UserType utilisateurType = user.getRole().getUserType();
//        System.out.println("----- " + utilisateurType.toString());
        if(userType==UserType.ADMIN && utilisateurType ==UserType.ADMIN) {
            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserType.ADMIN.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new User(user.getAdresseMail(), user.getMotDePasse(), authorities);
        } if(userType==UserType.CITIZEN && utilisateurType ==UserType.CITIZEN) {
            SimpleGrantedAuthority teacherAuthority = new SimpleGrantedAuthority(UserType.CITIZEN.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(teacherAuthority);
            return new User(user.getAdresseMail(), user.getMotDePasse(), authorities);
        }
        return null;
    }
}
