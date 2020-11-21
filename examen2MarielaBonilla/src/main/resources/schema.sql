DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(20) NOT NULL,
status INT NOT NULL DEFAULT 1
);

DROP TABLE IF EXISTS `workshop`;
CREATE TABLE `workshop` (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(20) NOT NULL,
objective VARCHAR(100) ,
keywords VARCHAR(100) ,
autor VARCHAR(20) NOT NULL,
id_categoria INT NOT NULL,
CONSTRAINT `fk_workshop_categoria` FOREIGN KEY (id_categoria) REFERENCES categoria (id)    
);

DROP TABLE IF EXISTS `actividad`;
CREATE TABLE `actividad` (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(20) NOT NULL,
description VARCHAR(100) NOT NULL,
texto VARCHAR(50) NOT NULL,
tiempo TIME NOT NULL,
id_workshop INT NOT NULL, 
CONSTRAINT `fk_actividad_workshop` FOREIGN KEY (id_workshop) REFERENCES workshop (id)
)