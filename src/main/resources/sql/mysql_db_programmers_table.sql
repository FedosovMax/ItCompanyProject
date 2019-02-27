CREATE DATABASE  IF NOT EXISTS `programmers` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `programmers`;


DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(150) NOT NULL,
  `company_country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`company_id`)
);

INSERT INTO `company` VALUES (1,'NASA','USA'),(2,'USLA','Germany'),(3,'SOID','Netherlands'),(4,'ChernigovFarm','Ukraine');


DROP TABLE IF EXISTS `company_project`;
CREATE TABLE `company_project` (
  `company_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  KEY `FK7morbsud0rn3v8itbsfsno2mw` (`project_id`),
  KEY `FKgo3oqrd6i7u6negclxyxg2stx` (`company_id`),
  CONSTRAINT `FK7morbsud0rn3v8itbsfsno2mw` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `FKgo3oqrd6i7u6negclxyxg2stx` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
);


DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `customer` VALUES (1,'Samsung','China'),(2,'HERAATI','Estonia'),(3,'THG','Hungary');


DROP TABLE IF EXISTS `customer_project`;
CREATE TABLE `customer_project` (
  `customer_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  KEY `FKs8wqfd9asiik7msoxisogy8yc` (`project_id`),
  KEY `FKnb0kcjkl2j8xdssyk4l6p5ja6` (`customer_id`),
  CONSTRAINT `FKnb0kcjkl2j8xdssyk4l6p5ja6` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FKs8wqfd9asiik7msoxisogy8yc` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
);

DROP TABLE IF EXISTS `developer`;
CREATE TABLE `developer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(25) DEFAULT NULL,
  `middle_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(35) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `skill_level_id` int(11) DEFAULT NULL,
  `language_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn4n7f0ugq45adk9v2veit08c1` (`skill_level_id`),
  KEY `FKf6h224s4mi15yktcmk6hff556` (`language_id`),
  CONSTRAINT `FKf6h224s4mi15yktcmk6hff556` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`),
  CONSTRAINT `FKn4n7f0ugq45adk9v2veit08c1` FOREIGN KEY (`skill_level_id`) REFERENCES `skill_level` (`id`)
);

INSERT INTO `developer` VALUES (1,'Antuan','Vladimirovich','Suvorov',58,3000,101,201),(2,'Sergey','Olegovich','Glavchenko',35,2500,101,201),(3,'Denis','Sergeevich','Savchenko',26,2000,102,201),(4,'don','adf','Ronald',23,3000,103,202),(5,'don','adf','Ronald',23,3000,102,203),(6,'don','adf','Ronald',23,3000,102,204);


DROP TABLE IF EXISTS `developer_project`;
CREATE TABLE `developer_project` (
  `developer_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`developer_id`,`project_id`),
  KEY `developers_projects_ibfk_2` (`project_id`),
  CONSTRAINT `developer_project_ibfk_1` FOREIGN KEY (`developer_id`) REFERENCES `developer` (`id`),
  CONSTRAINT `developer_project_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
);


INSERT INTO `developer_project` VALUES (1,2),(2,2),(5,2),(3,3),(5,3);


DROP TABLE IF EXISTS `developer_skill`;
CREATE TABLE `developer_skill` (
  `developer_id` int(11) NOT NULL,
  `skills_id` int(11) NOT NULL,
  PRIMARY KEY (`developer_id`,`skills_id`),
  KEY `developers_skills_ibfk_2` (`skills_id`),
  CONSTRAINT `developer_skill_ibfk_1` FOREIGN KEY (`developer_id`) REFERENCES `developer` (`id`),
  CONSTRAINT `developer_skill_ibfk_2` FOREIGN KEY (`skills_id`) REFERENCES `skill` (`id`)
);


INSERT INTO `developer_skill` VALUES (1,1),(2,3),(3,5);

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
);

INSERT INTO `hibernate_sequence` VALUES (1),(1);


DROP TABLE IF EXISTS `language`;

CREATE TABLE `language` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `language` VALUES (201,'Java'),(202,'C++'),(203,'C#'),(204,'JS');


DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(150) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `projects_ibfk_1` (`company_id`),
  KEY `projects_ibfk_2` (`customer_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `project` (`project_id`)
);

INSERT INTO `project` VALUES (1,'MARS','2008-05-12 00:00:00',1,1,30000),(2,'HOUSE','2006-07-15 00:00:00',2,2,25000),(3,'SOUTHGATE','2011-11-19 00:00:00',3,3,20000),(4,'project1',NULL,NULL,NULL,25000);


DROP TABLE IF EXISTS `skill`;

CREATE TABLE `skill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skills_level_id` int(11) DEFAULT NULL,
  `languages_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `skills_ibfk_2` (`skills_level_id`),
  KEY `skills_ibfk_3` (`languages_id`),
  CONSTRAINT `skill_ibfk_2` FOREIGN KEY (`skills_level_id`) REFERENCES `skill_level` (`id`),
  CONSTRAINT `skill_ibfk_3` FOREIGN KEY (`languages_id`) REFERENCES `language` (`id`)
);


INSERT INTO `skill` VALUES (1,101,201),(2,101,202),(3,101,203),(4,101,204),(5,102,201),(6,102,202),(7,102,203),(8,102,204),(9,103,201),(10,103,202),(11,103,203),(12,103,204);


DROP TABLE IF EXISTS `skill_level`;

CREATE TABLE `skill_level` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `skill_level` VALUES (101,'Junior'),(102,'Middle'),(103,'Senior');

