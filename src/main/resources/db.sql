
create database BankService;
CREATE TABLE `payee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cellnumber` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000;


CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `cellnumber` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `balance` decimal(10,3) DEFAULT '0.000',
  `password` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000;

