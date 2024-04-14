package com.ressourcesrelationnelles.config;
public class SecurityConstants {
    // milliseconde donc *1000 pour seconde
    public static final long JWT_EXPIRATION = 2 * 60 * 60 * 1000;
    public static final String JWT_SECERT = "pokemonsecretrandomstringwithmorethan256bits";

    public static final int PASSWORD_SIZE = 8;
}