-- ----------------------------
-- Generally speaking, if in China, you should append string `default-time-zone = '+08:00'`
-- to `/etc/mysql/mysql.conf.d/mysqld.cnf` config file.
-- Thus set mysql timezone to `UTC+0800`, to fix that
-- `CST` timezone will cause problem of more or less several hours.
-- ----------------------------

-- ----------------------------
-- 一般而言，需要在 `/etc/mysql/mysql.conf.d/mysqld.cnf` 尾部
-- 加上 `default-time-zone = '+08:00'`
-- 设定数据库时区为 UTC+0800，因为CST 时区会导致时间差问题
-- ----------------------------

-- ----------------------------
-- Database definition
-- ----------------------------
-- CREATE DATABASE `starter` DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for front_user
-- ----------------------------
CREATE TABLE `front_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL COMMENT 'username',
  `email` varchar(64) NOT NULL COMMENT 'email',
  `password` varchar(64) NOT NULL COMMENT 'password(encrypted)',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT 'status(1: normal, 0: blocked)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `front_user_idx_username` (`username`),
  UNIQUE KEY `front_user_idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='front_user';

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
  `front_user_id` bigint(20) NOT NULL COMMENT 'creator front_user id',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT 'status(1: normal, 0: blocked)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  KEY `article_idx_front_user_id` (`front_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='article';
