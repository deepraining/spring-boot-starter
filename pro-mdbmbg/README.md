# 说明

此项目仅用于 `pro-mdb` 演示用，并没有实质的代码

-----------------------------

先创建数据库 `starter_mdb`

```
CREATE DATABASE `starter_mdb` DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

再创建数据表 `mdb_article`

```
CREATE TABLE `mdb_article` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `intro` varchar(500) DEFAULT NULL COMMENT '简介',
  `content` text COMMENT '内容',
  `front_user_id` bigint(20) NOT NULL COMMENT '创建者 front_user id',
  `read_count` int(11) DEFAULT 0 COMMENT '阅读数',
  `support_count` int(11) DEFAULT 0 COMMENT '点赞数',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(1: 可用, 0: 禁用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `mdb_article_idx_front_user_id` (`front_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章';
```

再创建模型

```
./gradlew pro-mdbmbg:run
```
