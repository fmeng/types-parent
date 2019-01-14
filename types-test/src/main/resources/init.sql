CREATE DATABASE IF NOT EXISTS types
  DEFAULT CHARSET utf8
  COLLATE utf8_general_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`        int(11)      NOT NULL AUTO_INCREMENT,
  `username`  varchar(255) NOT NULL,
  `password`  varchar(255) NOT NULL,
  `pms`       int(11)               DEFAULT 0,
  `user_type` int(11)               DEFAULT 0,
  `address`   varchar(1024)         DEFAULT '',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;