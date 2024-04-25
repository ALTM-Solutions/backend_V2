package com.ressourcesrelationnelles.config;
public class SecurityConstants {
    // milliseconde donc *1000 pour seconde
    public static final long JWT_EXPIRATION = 2 * 60 * 60 * 1000; //2h
    // Clé privé du JWT
    public static final String JWT_SECERT = "pokemonsecretrandomstringwithmorethan256bits";
    //Taille minimale obligatoire du mot de passe
    public static final int PASSWORD_SIZE = 8;
}