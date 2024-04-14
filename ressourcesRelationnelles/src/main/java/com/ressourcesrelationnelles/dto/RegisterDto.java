package com.ressourcesrelationnelles.dto;

public class RegisterDto {
    private String adresseMail;
    private String password;

    private String nom;

    private String prenom;

    private String cheminPhotoProfil;


    public RegisterDto() {
    }

    public RegisterDto(String adresseMail, String password, String nom,String cheminPhotoProfil, String prenom) {
        this.adresseMail = adresseMail;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.cheminPhotoProfil = cheminPhotoProfil;
    }

    public String getCheminPhotoProfil() {
        return cheminPhotoProfil;
    }

    public void setCheminPhotoProfil(String cheminPhotoProfil) {
        this.cheminPhotoProfil = cheminPhotoProfil;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
