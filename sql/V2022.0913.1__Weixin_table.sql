CREATE TABLE `wx_user` (
  `id` bigint(20) NOT NULL COMMENT '主键Id(分布式生成Id)',
  `nickname` varchar(200) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT 0 COMMENT '性别（1：男，2：女，0：未知）',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province` varchar(50) DEFAULT NULL COMMENT '省',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `district` varchar(50) DEFAULT NULL COMMENT '区',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `register_date` datetime NOT NULL COMMENT '注册日期',
  `last_login` datetime DEFAULT NULL COMMENT '最后使用日期',
  `union_id` varchar(50) DEFAULT NULL COMMENT '微信unionId',
  `mini_open_id` varchar(50) DEFAULT NULL COMMENT '小程序openId',
  `mp_open_id` varchar(50) DEFAULT NULL COMMENT '公众号openId',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：-1 删除、0 禁用、1 启用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `wx_user_idx_phone`(`phone`),
  INDEX `wx_user_idx_union_id`(`union_id`),
  INDEX `wx_user_idx_mini_open_id`(`mini_open_id`),
  INDEX `wx_user_idx_mp_open_id`(`mp_open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信用户';

CREATE TABLE `wx_pay_trans` (
  `trans_id` varchar(50) NOT NULL COMMENT '支付流水号（微信支付回调的 transaction_id 字段）',
  `bill_no` varchar(50) NOT NULL COMMENT '业务单据号（微信支付回调的 out_trade_no 字段）',
  `open_id` varchar(50) DEFAULT NULL COMMENT '支付用户openId（微信支付回调的 openid 字段）',
  `mch_id` varchar(50) DEFAULT NULL COMMENT '收款商户号（微信支付回调的 mch_id 字段）',
  `app_id` varchar(50) NOT NULL COMMENT '应用的appId（微信支付回调的 appid 字段）',
  `total_fee` decimal(12, 2) NOT NULL COMMENT '订单金额（微信支付回调的 total_fee/100 字段）',
  `cash_fee` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '支付金额（微信支付回调的 cash_fee/100 字段）',
  `coupon_fee` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠券支付金额（微信支付回调的 coupon_fee/100 字段）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`trans_id`),
  INDEX `wx_pay_trans_idx_bill_no`(`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信支付流水';
