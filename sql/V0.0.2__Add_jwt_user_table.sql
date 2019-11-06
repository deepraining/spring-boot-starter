-- ----------------------------
-- Table structure for jwt_user
-- ----------------------------
CREATE TABLE `jwt_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT 'username',
  `password` varchar(64) NOT NULL COMMENT 'password(encrypted)',
  `deleted` int(1) DEFAULT 0 COMMENT 'deleted or not',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COMMENT='jwt_user';
