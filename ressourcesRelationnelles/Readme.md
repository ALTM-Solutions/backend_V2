# RESOURCES RELATIONNELLES

## Initialisation
- Cloner ce repo
- Créer une nouvelle branche

# Projet avec Docker
Ce projet utilise Docker pour gérer les conteneurs. Voici comment le lancer :

## Pour lancer seulement la base de données (méthode de développement)
1. Depuis le répertoire `backend` `mysql_server`, ouvrez un terminal (par exemple, GitBash) et exécutez la commande suivante :
   `docker-compose up -d`

    - L'option `-d` signifie "détaché", ce qui permet de lancer les conteneurs en arrière-plan.
    - Le fichier `docker-compose.yml` définit la composition des conteneurs pour notre architecture.
- Vous verrez le lancement dans l'application Docker Desktop :

  | Name        | Image        | Status       |
  |-------------|--------------|--------------|
  | mysql.serv  | Running (1/1)|              |

2. Dans IntelliJ :
- Ouvrez le dossier `backend/ressourcesRelationnelles`.
- Naviguez vers `src/main/resources/application.properties`.
- Commentez la première ligne(spring.datasource.url=jdbc:mysql://localhost:3307/ressourcesrelationnelles) et décommentez la deuxième ligne(#spring.datasource.url=jdbc:mysql://mysql-server:3306/ressourcesrelationnelles).

L'application est opérationnelle, vous pouvez la lancer depuis `ResourcesRelationnellesApplication`.

## Pour lancer toute l'application (mode production)
1. Suivez le même processus que pour l'étape 1, mais dans le répertoire `backend`.

2. Dans IntelliJ :
- Ouvrez les dossiers `backend` et `ressourcesRelationnelles`.
- Naviguez vers `src/main/resources/application.properties`.
- Commentez la deuxième ligne et décommentez la première ligne.

**ATTENTION :** En mode production, pensez à supprimer l'image Docker à chaque mise à jour du code pour éviter d'utiliser une ancienne version compilée.