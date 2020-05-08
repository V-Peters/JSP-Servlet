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
	('8', '2'),
	('2', '4'),
	('1', '5'),
	('15', '5'),
	('2', '5');