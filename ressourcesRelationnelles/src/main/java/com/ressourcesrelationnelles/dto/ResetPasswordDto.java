package com.ressourcesrelationnelles.dto;

/*
Un DTO :
    Permet d'avoir un "template" de données
    Comme une capsule avec un moule prédéfinit, on le remplit et on sait que quand on le transfert à quelqu'un il est sûr de ce qu'il y a dedans
    Quand donnée à un controller, peut aussi générer le JSON par rapport au DTO
 */
public class ResetPasswordDto {

    private String email;

    public ResetPasswordDto(String email) {
        this.email = email;
    }

    public ResetPasswordDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
