-- MySQL Script generated by MySQL Workbench
-- Sun May  5 15:44:36 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ressourcesrelationnelles
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ressourcesrelationnelles
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ressourcesrelationnelles` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ressourcesrelationnelles` ;

-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`piece_jointe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`piece_jointe` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `chemin_piece_jointe` VARCHAR(255) NOT NULL,
  `nom_origine` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`utilisateur` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  `prenom` VARCHAR(100) NOT NULL,
  `adresse_mail` VARCHAR(255) NOT NULL,
  `mot_de_passe` VARCHAR(100) NOT NULL,
  `chemin_photo_profil` VARCHAR(255) NOT NULL,
  `date_desactive` DATETIME NULL DEFAULT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_utilisateur_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_utilisateur_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `ressourcesrelationnelles`.`role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`ressource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`ressource` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_publication` DATETIME NOT NULL,
  `date_modification` DATETIME NULL DEFAULT NULL,
  `text` LONGTEXT NOT NULL,
  `visibilite` TINYINT(4) NOT NULL,
  `valide` TINYINT(4) NOT NULL,
  `nom_ressource` VARCHAR(255) NOT NULL,
  `utilisateur_id` INT(11) NOT NULL,
  `piece_jointe_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ressource_utilisateur1_idx` (`utilisateur_id` ASC) VISIBLE,
  INDEX `fk_ressource_piece_jointe1_idx` (`piece_jointe_id` ASC) VISIBLE,
  CONSTRAINT `fk_ressource_piece_jointe1`
    FOREIGN KEY (`piece_jointe_id`)
    REFERENCES `ressourcesrelationnelles`.`piece_jointe` (`id`),
  CONSTRAINT `fk_ressource_utilisateur1`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`commentaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`commentaire` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `contenu` LONGTEXT NOT NULL,
  `date_commentaire` DATETIME NOT NULL,
  `ressource_id` INT(11) NOT NULL,
  `utilisateur_id` INT(11) NOT NULL,
  `piece_jointe_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_commentaire_ressource1_idx` (`ressource_id` ASC) VISIBLE,
  INDEX `fk_commentaire_utilisateur1_idx` (`utilisateur_id` ASC) VISIBLE,
  INDEX `fk_commentaire_piece_jointe1_idx` (`piece_jointe_id` ASC) VISIBLE,
  CONSTRAINT `fk_commentaire_piece_jointe1`
    FOREIGN KEY (`piece_jointe_id`)
    REFERENCES `ressourcesrelationnelles`.`piece_jointe` (`id`),
  CONSTRAINT `fk_commentaire_ressource1`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_commentaire_utilisateur1`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`message` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(5000) NOT NULL,
  `date_envoi` DATETIME NOT NULL,
  `envoyeur` INT(11) NOT NULL,
  `ressource_id` INT(11) NOT NULL,
  `piece_jointe_id` INT(11) NULL DEFAULT NULL,
  `recepteur` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_utilisateur1_idx` (`envoyeur` ASC) VISIBLE,
  INDEX `fk_message_ressource1_idx` (`ressource_id` ASC) VISIBLE,
  INDEX `fk_message_piece_jointe1_idx` (`piece_jointe_id` ASC) VISIBLE,
  INDEX `fk_message_utilisateur2_idx` (`recepteur` ASC) VISIBLE,
  CONSTRAINT `fk_message_piece_jointe1`
    FOREIGN KEY (`piece_jointe_id`)
    REFERENCES `ressourcesrelationnelles`.`piece_jointe` (`id`),
  CONSTRAINT `fk_message_ressource1`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_message_utilisateur1`
    FOREIGN KEY (`envoyeur`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_message_utilisateur2`
    FOREIGN KEY (`recepteur`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`type_parcours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`type_parcours` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`progression`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`progression` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `utilisateur_id` INT(11) NOT NULL,
  `ressource_id` INT(11) NOT NULL,
  `statut` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_progression_utilisateur1_idx` (`utilisateur_id` ASC) VISIBLE,
  INDEX `fk_progression_ressource1_idx` (`ressource_id` ASC) VISIBLE,
  CONSTRAINT `fk_progression_ressource1`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_progression_utilisateur1`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`reponse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`reponse` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `reponse` TEXT NOT NULL,
  `date` DATETIME NOT NULL,
  `piece_jointe_id` INT(11) NULL DEFAULT NULL,
  `commentaire_id` INT(11) NOT NULL,
  `utilisateur_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_reponse_piece_jointe1_idx` (`piece_jointe_id` ASC) VISIBLE,
  INDEX `fk_reponse_commentaire1_idx` (`commentaire_id` ASC) VISIBLE,
  INDEX `fk_reponse_utilisateur1_idx` (`utilisateur_id` ASC) VISIBLE,
  CONSTRAINT `fk_reponse_commentaire1`
    FOREIGN KEY (`commentaire_id`)
    REFERENCES `ressourcesrelationnelles`.`commentaire` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_reponse_piece_jointe1`
    FOREIGN KEY (`piece_jointe_id`)
    REFERENCES `ressourcesrelationnelles`.`piece_jointe` (`id`),
  CONSTRAINT `fk_reponse_utilisateur1`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`validation_utilisateur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`validation_utilisateur` (
  `id_validation_utilisateur` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `code` VARCHAR(16) NOT NULL,
  `expiration_date` DATETIME NOT NULL,
  PRIMARY KEY (`id_validation_utilisateur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`favorite` (
  `ressource_id` INT(11) NOT NULL,
  `utilisateur_id` INT(11) NOT NULL,
  PRIMARY KEY (`ressource_id`, `utilisateur_id`),
  INDEX `fk_ressource_has_utilisateur_utilisateur1_idx` (`utilisateur_id` ASC) VISIBLE,
  INDEX `fk_ressource_has_utilisateur_ressource1_idx` (`ressource_id` ASC) VISIBLE,
  CONSTRAINT `fk_ressource_has_utilisateur_ressource1`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ressource_has_utilisateur_utilisateur1`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`mis_de_cote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`mis_de_cote` (
  `ressource_id` INT(11) NOT NULL,
  `utilisateur_id` INT(11) NOT NULL,
  PRIMARY KEY (`ressource_id`, `utilisateur_id`),
  INDEX `fk_ressource_has_utilisateur_utilisateur2_idx` (`utilisateur_id` ASC) VISIBLE,
  INDEX `fk_ressource_has_utilisateur_ressource2_idx` (`ressource_id` ASC) VISIBLE,
  CONSTRAINT `fk_ressource_has_utilisateur_ressource2`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ressource_has_utilisateur_utilisateur2`
    FOREIGN KEY (`utilisateur_id`)
    REFERENCES `ressourcesrelationnelles`.`utilisateur` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ressourcesrelationnelles`.`type_parcours_has_ressource`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ressourcesrelationnelles`.`type_parcours_has_ressource` (
  `type_parcours_id` INT(11) NOT NULL,
  `ressource_id` INT(11) NOT NULL,
  PRIMARY KEY (`type_parcours_id`, `ressource_id`),
  INDEX `fk_type_parcours_has_ressource_ressource1_idx` (`ressource_id` ASC) VISIBLE,
  INDEX `fk_type_parcours_has_ressource_type_parcours1_idx` (`type_parcours_id` ASC) VISIBLE,
  CONSTRAINT `fk_type_parcours_has_ressource_type_parcours1`
    FOREIGN KEY (`type_parcours_id`)
    REFERENCES `ressourcesrelationnelles`.`type_parcours` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_type_parcours_has_ressource_ressource1`
    FOREIGN KEY (`ressource_id`)
    REFERENCES `ressourcesrelationnelles`.`ressource` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
