DROP TABLE IF EXISTS `jsp_test`.`meeting_user`;

CREATE TABLE `jsp_test`.`meeting_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_meeting` INT NOT NULL,
  `id_user` INT NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`));


INSERT INTO `jsp_test`.`meeting_user` (
    `id_meeting`,
    `id_user`) 
VALUES
	('1', '2'),
	('2', '2'),
	('3', '2'),
	('1', '3'),
	('3', '3'),
	('5', '3'),
	('2', '4'),
	('3', '4'),
	('4', '4'),
	('3', '5'),
	('4', '5'),
	('6', '5'),
	('4', '6'),
	('7', '6'),
	('8', '6');