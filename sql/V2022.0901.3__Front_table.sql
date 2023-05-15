CREATE TABLE `front_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `email` varchar(64) NOT NULL COMMENT '电子邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：-1 删除、0 禁用、1 启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `front_user_idx_username` (`username`),
  INDEX `front_user_idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='前端用户';

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `intro` varchar(500) DEFAULT NULL COMMENT '简介',
  `content` text COMMENT '内容',
  `front_user_id` bigint(20) NOT NULL COMMENT '创建者 front_user id',
  `read_count` int(11) DEFAULT 0 COMMENT '阅读数',
  `support_count` int(11) DEFAULT 0 COMMENT '点赞数',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：-1 删除、0 禁用、1 启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `article_idx_front_user_id` (`front_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章';
