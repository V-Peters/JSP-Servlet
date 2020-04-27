DROP TABLE IF EXISTS `jsp_test`.`user`;

CREATE TABLE `jsp_test`.`user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` varchar(50) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `company` varchar(100) NOT NULL,
  `created` datetime DEFAULT now(),
  `last_updated` datetime DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

INSERT INTO `jsp_test`.`user` (
    `username`,
    `password`,
    `vorname`,
    `lastname`,
    `email`,
    `company`) 
VALUES
	('vpet', '12345', 'Victor', 'Peters', 'V.Peters@iks-gmbh.com', 'IKS');