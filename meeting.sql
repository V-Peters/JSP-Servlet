DROP TABLE IF EXISTS `jsp_test`.`meeting`;

CREATE TABLE `jsp_test`.`meeting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `date_time` datetime NOT NULL,
  `display` tinyint(1) DEFAULT '1',
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);


INSERT INTO `jsp_test`.`meeting` (
    `name`,
    `date_time`) 
VALUES
	('Thementag 1', '2020-06-01 10:00:00'),
	('Ernährungsberatung', '2020-06-01 12:00:00'),
	('Workshop Datenverwaltung', '2020-06-01 14:00:00'),
	('Vortrag Algorithmen', '2020-06-20 10:30:00'),
	('Vartrag Datenstrukturen', '2020-06-20 12:30:00'),
	('SQL Einführung', '2020-06-06 08:30:00'),
	('Einführung in komplexe Systeme', '2020-06-06 15:00:00');