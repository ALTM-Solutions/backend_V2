package com.ressourcesrelationnelles.service;

import com.ressourcesrelationnelles.config.SecurityConstants;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEmailWithCode(String email, String code){
        CompletableFuture.runAsync(() -> {
            String bodyMail = "Voici votre code de validation pour la création de votre compte :\n\n" + code + "\n\n le code expirera dans " + SecurityConstants.MINUTE_EXPIRE + " minutes.";
            this.sendEmail(email, "[ReSource-Relationnelles] Validation du code utilisateur", bodyMail);
        });
    }
    public void sendEmailWithNewPassword(String email, String password){
        CompletableFuture.runAsync(() -> {
            String bodyMail = "Voici votre nouveau mot de passe :\n\n" + password + "\n\nIl est recommandé de changer le mot de passe après votre première connexion.";
            this.sendEmail(email, "[ReSource-Relationnelles] Reinitialisation password", bodyMail);
        });
    }

}