-- ----------------------------
-- Table structure for jwt_user
-- ----------------------------
CREATE TABLE `jwt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(30) NOT NULL DEFAULT (CONCAT(UUID_SHORT(), SUBSTR(RAND(), 3, 8))) COMMENT 'uuid',
  `username` varchar(64) NOT NULL COMMENT 'username',
  `password` varchar(64) NOT NULL COMMENT 'password(encrypted)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT 'deleted or not',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT 'update time',
  PRIMARY KEY (`id`),
  KEY `idx_uuid` (`uuid`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='jwt_user table';
