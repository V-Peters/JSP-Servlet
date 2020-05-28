DROP TABLE IF EXISTS `jsp_test`.`user`;

CREATE TABLE `jsp_test`.`user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company` varchar(100) NOT NULL,
  `is_admin` tinyint(1) DEFAULT '0',
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

INSERT INTO `jsp_test`.`user` (
    `username`,
    `password`,
    `firstname`,
    `lastname`,
    `email`,
    `company`,
    `is_admin`) 
VALUES
	('admin', 'admin', 'admin', 'admin', 'admin', 'admin', 1),
	('vpet', '12345', 'Victor', 'Peters', 'V.Peters@iks-gmbh.com', 'IKS', 0),
	('mmus', '12345', 'Max', 'Mustermann', 'M.Mustermann@beispielfirma.com', 'Beispielfirma', 0),
	('gdin', '12345', 'Gerda', 'Dinkel', 'G.Dinkel@email.com', 'Post', 0),
	('sfle', '12345', 'Sammy', 'Fleischbällchen', 'S.Fleisch@hotmail.com', 'Burgerking', 0),
	('smue', '12345', 'Sabine', 'Müller', 'S.Müller@gmail.com', 'DHL', 0);