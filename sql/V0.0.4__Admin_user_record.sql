-- ----------------------------
-- Records of admin_user
-- ----------------------------

-- ----------------------------
-- 方法一
--
-- 通过 /admin/register 接口，依次注册下面3个用户（可以自己设定密码，可以用 postman 操作）
-- username: manager, password: starter123456, nickname: 管理员
-- username: productManager, password: starter123456, nickname: 商品管理员
-- username: orderManager, password: starter123456, nickname: 订单管理员
--
-- 然后更新他们的id
-- UPDATE `admin_user` set id=1 WHERE username='manager';
-- UPDATE `admin_user` set id=2 WHERE username='productManager';
-- UPDATE `admin_user` set id=3 WHERE username='orderManager';
-- ----------------------------

-- 方法二：打开下面的注释
INSERT INTO `admin_user` VALUES ('1', 'manager', '$2y$12$3C4Rqr0Hph4woy4zEXICdeunwsR/nCsfSGSF49bX4/T603Byvr2q2', null , null , '管理员', null, null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_user` VALUES ('2', 'productManager', '$2y$12$3C4Rqr0Hph4woy4zEXICdeunwsR/nCsfSGSF49bX4/T603Byvr2q2', null , null , '商品管理员', null, null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_user` VALUES ('3', 'orderManager', '$2y$12$3C4Rqr0Hph4woy4zEXICdeunwsR/nCsfSGSF49bX4/T603Byvr2q2', null , null , '订单管理员', null, null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1', '商品管理员', '只能查看及操作商品', '0', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role` VALUES ('2', '订单管理员', '只能查看及操作订单', '0', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role` VALUES ('5', '超级管理员', '拥有所有查看和操作功能', '0', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_permission
-- 似乎没有作用
-- ----------------------------
INSERT INTO `admin_permission` VALUES ('1', '0', '商品', null, null, '0', null, '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('2', '1', '商品列表', 'product:read', null, '1', '/product/index', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('3', '1', '添加商品', 'product:create', null, '1', '/product/add', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('4', '1', '商品分类', 'productCategory:read', null, '1', '/productCate/index', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('5', '1', '商品类型', 'productAttribute:read', null, '1', '/productAttr/index', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('6', '1', '品牌管理', 'brand:read', null, '1', '/brand/index', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('7', '2', '编辑商品', 'product:update', null, '2', '/product/updateProduct', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('8', '2', '删除商品', 'product:delete', null, '2', '/product/delete', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('9', '4', '添加商品分类', 'productCategory:create', null, '2', '/productCate/create', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('10', '4', '修改商品分类', 'productCategory:update', null, '2', '/productCate/update', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('11', '4', '删除商品分类', 'productCategory:delete', null, '2', '/productAttr/delete', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('12', '5', '添加商品类型', 'productAttribute:create', null, '2', '/productAttr/create', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('13', '5', '修改商品类型', 'productAttribute:update', null, '2', '/productAttr/update', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('14', '5', '删除商品类型', 'productAttribute:delete', null, '2', '/productAttr/delete', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('15', '6', '添加品牌', 'brand:create', null, '2', '/brand/add', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('16', '6', '修改品牌', 'brand:update', null, '2', '/brand/update', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('17', '6', '删除品牌', 'brand:delete', null, '2', '/brand/delete', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_permission` VALUES ('18', '0', '首页', null, null, '0', null, '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_user_role_relation
-- ----------------------------
INSERT INTO `admin_user_role_relation` VALUES ('1', '1', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_user_role_relation` VALUES ('2', '2', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_user_role_relation` VALUES ('3', '3', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_user_permission_relation
-- ----------------------------

-- ----------------------------
-- Records of admin_role_permission_relation
-- 似乎没有作用
-- ----------------------------
INSERT INTO `admin_role_permission_relation` VALUES ('1', '1', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('2', '1', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('3', '1', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('4', '1', '7', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('5', '1', '8', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('6', '2', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('7', '2', '9', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('8', '2', '10', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('9', '2', '11', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('10', '3', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('11', '3', '12', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('12', '3', '13', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('13', '3', '14', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('14', '4', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('15', '4', '15', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('16', '4', '16', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_permission_relation` VALUES ('17', '4', '17', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES ('1', '0', '商品', '0', '0', 'pms', 'product', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('2', '1', '商品列表', '1', '0', 'product', 'product-list', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('3', '1', '添加商品', '1', '0', 'addProduct', 'product-add', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('4', '1', '商品分类', '1', '0', 'productCate', 'product-cate', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('5', '1', '商品类型', '1', '0', 'productAttr', 'product-attr', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('6', '1', '品牌管理', '1', '0', 'brand', 'product-brand', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('7', '0', '订单', '0', '0', 'oms', 'order', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('8', '7', '订单列表', '1', '0', 'order', 'product-list', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('9', '7', '订单设置', '1', '0', 'orderSetting', 'order-setting', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('10', '7', '退货申请处理', '1', '0', 'returnApply', 'order-return', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('11', '7', '退货原因设置', '1', '0', 'returnReason', 'order-return-reason', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('12', '0', '营销', '0', '0', 'sms', 'sms', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('13', '12', '秒杀活动列表', '1', '0', 'flash', 'sms-flash', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('14', '12', '优惠券列表', '1', '0', 'coupon', 'sms-coupon', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('16', '12', '品牌推荐', '1', '0', 'homeBrand', 'product-brand', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('17', '12', '新品推荐', '1', '0', 'homeNew', 'sms-new', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('18', '12', '人气推荐', '1', '0', 'homeHot', 'sms-hot', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('19', '12', '专题推荐', '1', '0', 'homeSubject', 'sms-subject', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('20', '12', '广告列表', '1', '0', 'homeAdvertise', 'sms-ad', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('21', '0', '权限', '0', '0', 'ums', 'ums', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('22', '21', '用户列表', '1', '0', 'admin', 'ums-admin', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('23', '21', '角色列表', '1', '0', 'role', 'ums-role', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('24', '21', '菜单列表', '1', '0', 'menu', 'ums-menu', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('25', '21', '资源列表', '1', '0', 'resource', 'ums-resource', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES ('1', '商品品牌管理', '/brand/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('2', '商品属性分类管理', '/productAttribute/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('3', '商品属性管理', '/productAttribute/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('4', '商品分类管理', '/productCategory/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('5', '商品管理', '/product/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('6', '商品库存管理', '/sku/**', null, '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('8', '订单管理', '/order/**', '', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('9', '订单退货申请管理', '/returnApply/**', '', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('10', '退货原因管理', '/returnReason/**', '', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('11', '订单设置管理', '/orderSetting/**', '', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('12', '收货地址管理', '/companyAddress/**', '', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('13', '优惠券管理', '/coupon/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('14', '优惠券领取记录管理', '/couponHistory/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('15', '限时购活动管理', '/flash/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('16', '限时购商品关系管理', '/flashProductRelation/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('17', '限时购场次管理', '/flashSession/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('18', '首页轮播广告管理', '/home/advertise/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('19', '首页品牌管理', '/home/brand/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('20', '首页新品管理', '/home/newProduct/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('21', '首页人气推荐管理', '/home/recommendProduct/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('22', '首页专题推荐管理', '/home/recommendSubject/**', '', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('23', '商品优选管理', '/prefrenceArea/**', '', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('24', '商品专题管理', '/subject/**', '', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('25', '后台用户管理', '/admin/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('26', '后台用户角色管理', '/role/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('27', '后台菜单管理', '/menu/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('28', '后台资源分类管理', '/resourceCategory/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('29', '后台资源管理', '/resource/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_resource_category
-- ----------------------------
INSERT INTO `admin_resource_category` VALUES ('1', '商品模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('2', '订单模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('3', '营销模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('4', '权限模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('5', '内容模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('6', '其他模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_role_menu_relation
-- ----------------------------
INSERT INTO `admin_role_menu_relation` VALUES ('33', '1', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('34', '1', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('35', '1', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('36', '1', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('37', '1', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('38', '1', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('53', '2', '7', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('54', '2', '8', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('55', '2', '9', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('56', '2', '10', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('57', '2', '11', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('72', '5', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('73', '5', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('74', '5', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('75', '5', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('76', '5', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('77', '5', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('78', '5', '7', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('79', '5', '8', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('80', '5', '9', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('81', '5', '10', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('82', '5', '11', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('83', '5', '12', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('84', '5', '13', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('85', '5', '14', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('86', '5', '16', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('87', '5', '17', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('88', '5', '18', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('89', '5', '19', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('90', '5', '20', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('91', '5', '21', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('92', '5', '22', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('93', '5', '23', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('94', '5', '24', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('95', '5', '25', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_role_resource_relation
-- ----------------------------
INSERT INTO `admin_role_resource_relation` VALUES ('103', '2', '8', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('104', '2', '9', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('105', '2', '10', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('106', '2', '11', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('107', '2', '12', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('142', '5', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('143', '5', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('144', '5', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('145', '5', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('146', '5', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('147', '5', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('148', '5', '8', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('149', '5', '9', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('150', '5', '10', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('151', '5', '11', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('152', '5', '12', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('153', '5', '13', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('154', '5', '14', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('155', '5', '15', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('156', '5', '16', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('157', '5', '17', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('158', '5', '18', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('159', '5', '19', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('160', '5', '20', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('161', '5', '21', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('162', '5', '22', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('163', '5', '23', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('164', '5', '24', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('165', '5', '25', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('166', '5', '26', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('167', '5', '27', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('168', '5', '28', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('169', '5', '29', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('170', '1', '1', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('171', '1', '2', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('172', '1', '3', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('173', '1', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('174', '1', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('175', '1', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('176', '1', '23', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('177', '1', '24', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
