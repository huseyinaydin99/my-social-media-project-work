CREATE DATABASE IF NOT EXISTS `firmaveritabani` 
				   DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `firmaveritabani`.`personel` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `adi` VARCHAR(45) NULL COMMENT '',
  `soyadi` VARCHAR(45) NULL COMMENT '',
  `cinsiyeti` VARCHAR(10) NULL COMMENT '',
  `eposta` VARCHAR(100) NULL COMMENT '',
  `numarasi` INT NULL COMMENT '',
  `parolasi` VARCHAR(100) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '');
    
INSERT INTO `firmaveritabani`.`personel` 
(`adi`, `soyadi`, `cinsiyeti`, `eposta`, `numarasi`, `parolasi`) 
VALUES 
('Mimar', 'Aslan', '2', 'mimaraslan@yandex.ru', '1040', '12991453');

INSERT INTO `firmaveritabani`.`personel` 
(`adi`, `soyadi`,  `cinsiyeti`, `eposta`, `numarasi`, `parolasi`) 
VALUES 
('Uğur', 'Ata', '2', 'ataugur@gmail.com', '1517',  '571632');

INSERT INTO `firmaveritabani`.`personel` 
(`adi`, `soyadi`,  `cinsiyeti`, `eposta`, `numarasi`, `parolasi`) 
VALUES 
('Katerina', 'Uhtiy', '1', 'katerina@ya.ru', '1326', '1234RU');

SELECT * FROM `firmaveritabani`.`personel`;