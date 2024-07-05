package com.ressourcesrelationnelles.config;
public class SecurityConstants {
    // milliseconde donc *1000 pour seconde
    public static final long JWT_EXPIRATION = 2 * 60 * 60 * 1000; //2h
    // Clé privé du JWT
    public static final String JWT_SECERT = "pokemonsecretrandomstringwithmorethan256bits";
    //Taille minimale obligatoire du mot de passe
    public static final int PASSWORD_SIZE = 8;

    // Taille du code de validation lors de la création d'un compte
    public static final int VALIDATION_CODE_SIZE = 6;

    // Temps avant expiration du code de validation
    public static final int MINUTE_EXPIRE = 5;

    /* Fonctionnement du cron expression :
    les asterix / position correspondes à :
        1) second
        2) minutes
        3) heures
        4) jour du mois
        5) mois
        6) jour de la semaine

    Pour réaliser une action toutes les x/position il suffit d'ecrire * / 1
    par exemple :*/

    // 0 */1 * * * * => Toutes les minutes
    // */30 * * * * * => Toutes les 30 secondes
    // 0 0 * * * * => Toutes heures à h:00:00
    // 0 0 0 * * * => Tous les jours à minuit
    // 0 0 12 * * * => tous les jours à midi

    public static final String CRON_EXPRESSION = "0 0 0 * * *";
}