-- ----------------------------
-- Generally speaking, if in China, you should append string `default-time-zone = '+08:00'`
-- to `/etc/mysql/mysql.conf.d/mysqld.cnf` config file.
-- Thus set mysql timezone to `UTC+0800`,
-- for `CST` timezone will cause problem of more or less several hours.
-- ----------------------------

-- ----------------------------
-- 一般而言，需要在 `/etc/mysql/mysql.conf.d/mysqld.cnf` 尾部
-- 加上 `default-time-zone = '+08:00'`
-- 设定数据库时区为 UTC+0800，CST 时区会导致时间差问题
-- ----------------------------

-- ----------------------------
-- Database definition
-- ----------------------------
-- CREATE DATABASE `starter` DEFAULT CHARSET=utf8mb4 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL COMMENT 'username',
  `email` varchar(64) NOT NULL COMMENT 'email',
  `password` varchar(64) NOT NULL COMMENT 'password(encrypted)',
  `deleted` int(1) DEFAULT 0 COMMENT 'deleted or not',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='user';

-- ----------------------------
-- Table structure for article
-- ----------------------------
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT 'title',
  `read_count` int(11) DEFAULT 0 COMMENT 'read count',
  `support_count` int(11) DEFAULT 0 COMMENT 'support count',
  `intro` varchar(500) DEFAULT NULL COMMENT 'article summary',
  `content` text COMMENT 'article content',
  `create_user_id` bigint(20) NOT NULL COMMENT 'creator user_id',
  `deleted` int(1) DEFAULT 0 COMMENT 'deleted or not',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  KEY `idx_create_user_id` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='article';
