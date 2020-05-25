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
	('Thementag 1', '2020-01-01 00:00:00'),
	('dies ist ein Test', '2020-01-01 00:00:00'),
	('dies ist ein anderer Test', '2020-01-01 00:00:00'),
	('Veranstaltungen hinzufügen geht', '2020-01-01 00:00:00'),
	('nur mit den Umlauten scheint es Probleme zu geben', '2020-01-01 00:00:00'),
	('Änderungen übernehmen klappt noch nicht, soll aber auch nur dazu da sein die Veränderungen in der', '2020-01-01 00:00:00'),
	('Spalte "anzeigen?" zu speichern, man kann sie aber auch über den Link bearbeiten verändern', '2020-01-01 00:00:00'),
	('Viel Spaß mit diesem Test', '2020-01-01 00:00:00');