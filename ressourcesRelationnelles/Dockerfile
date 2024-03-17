# Utilisez une image de base Maven avec JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez le pom.xml et les sources dans le conteneur
COPY pom.xml .
COPY src ./src

# Compilez l'application avec Maven
RUN mvn clean package

# Utilisez une image de base OpenJDK 21 pour exécuter l'application
FROM openjdk:21-jdk

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez le jar dans le conteneur
COPY --from=0 /app/target/*.jar ./ressourcesRelationnelles.jar

# Exposez le port sur lequel l'application s'exécute
EXPOSE 8080

# Définissez la commande pour exécuter l'application
CMD ["java", "-jar", "ressourcesRelationnelles.jar"]
