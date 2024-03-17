package com.ressourcesrelationnelles;

import com.ressourcesrelationnelles.config.FileStorageProperties;
import com.ressourcesrelationnelles.config.HostProperties;
import com.ressourcesrelationnelles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class,
		HostProperties.class
})
public class RessourcesRelationnellesApplication {

	@Autowired
	private RoleService roleService;
	public static void main(String[] args) {
		SpringApplication.run(RessourcesRelationnellesApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		try {
			roleService.createAllUserType();
		} catch (Exception e) {
			System.out.println("Erreur lors de la création des roles : " + e.getMessage());
		}
	}

}
