DROP TABLE IF EXISTS `jsp_test`.`meetings_user_connection`;

CREATE TABLE `jsp_test`.`meetings_user_connection` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_meeting` INT NOT NULL,
  `id_user` INT NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`));


INSERT INTO `jsp_test`.`meetings_user_connection` (
    `id_meeting`,
    `id_user`) 
VALUES
	('1', '1'),
	('2', '1'),
	('3', '1'),
	('1', '2'),
	('3', '2'),
	('5', '2'),
	('2', '3'),
	('3', '3'),
	('4', '3'),
	('3', '4'),
	('4', '4'),
	('6', '4'),
	('4', '5'),
	('7', '5'),
	('8', '5');