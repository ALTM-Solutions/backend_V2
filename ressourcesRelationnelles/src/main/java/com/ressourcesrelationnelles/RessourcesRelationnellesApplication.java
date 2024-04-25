package com.ressourcesrelationnelles;

import com.ressourcesrelationnelles.config.FileStorageProperties;
import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.dto.RegisterDto;
import com.ressourcesrelationnelles.model.UserType;
import com.ressourcesrelationnelles.service.AuthService;
import com.ressourcesrelationnelles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

// Application SpringBoot
@SpringBootApplication
// Propriétés de configurations spécifiques
@EnableConfigurationProperties({
		FileStorageProperties.class,
		HostProperties.class
})
public class RessourcesRelationnellesApplication {

	// Injection du rôle Service
	@Autowired
	private RoleService roleService;

	@Autowired
	private AuthService authService;
	// Démarrage de l'application Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(RessourcesRelationnellesApplication.class, args);
	}

	// Méthode exécutée après le démarrage de l'application
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		try {
			// Création de tous les rôles
			roleService.createAllUserType();
		} catch (Exception e) {
			System.out.println("Erreur lors de la création des roles : " + e.getMessage());
		}
		try{
			authService.createUser(new RegisterDto("super-z@admin.fr", "ABCdefghijklmnopqrstuvwxyz123456789.", "ADMIN","","Super"), UserType.SUPER_ADMIN);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
