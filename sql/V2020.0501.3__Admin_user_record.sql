-- ----------------------------
-- Records of admin_user
--
-- 通过 /admin/register 接口，注册manager用户（可以自己设定密码，可以用 postman 或浏览器操作）
-- username: manager, password: starter123456, nickname: 管理员
--
-- 然后更新id（如果不是下面的id）
-- UPDATE `admin_user` set id=1 WHERE username='manager';
-- ----------------------------

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('5', '超级管理员', '拥有所有查看和操作功能', '1', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_user_role_relation
-- ----------------------------
INSERT INTO `admin_user_role_relation` VALUES ('1', '1', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES ('21', '0', '权限', '0', '0', 'ums', 'ums', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('22', '21', '用户列表', '1', '0', 'umsAdmin', 'ums-admin', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('23', '21', '角色列表', '1', '0', 'umsRole', 'ums-role', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('24', '21', '菜单列表', '1', '0', 'umsMenu', 'ums-menu', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('25', '21', '资源列表', '1', '0', 'umsResource', 'ums-resource', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('26', '0', '前端用户', '0', '0', 'frontUser', 'angle-right', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('27', '26', '前端用户列表', '1', '0', 'frontUserList', 'angle-double-right', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('28', '0', '文章', '0', '0', 'article', 'angle-right', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_menu` VALUES ('29', '28', '文章列表', '1', '0', 'articleList', 'angle-double-right', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES ('25', '后台用户管理', '/admin/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('26', '后台用户角色管理', '/adminRole/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('27', '后台菜单管理', '/adminMenu/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('28', '后台资源分类管理', '/adminResourceCategory/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('29', '后台资源管理', '/adminResource/**', '', '4', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('30', '前端用户管理', '/frontUser/**', '', '5', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource` VALUES ('31', '文章管理', '/article/**', '', '6', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_resource_category
-- ----------------------------
INSERT INTO `admin_resource_category` VALUES ('4', '权限模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('5', '前端用户模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_resource_category` VALUES ('6', '文章模块', '0', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_role_menu_relation
-- ----------------------------
INSERT INTO `admin_role_menu_relation` VALUES ('91', '5', '21', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('92', '5', '22', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('93', '5', '23', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('94', '5', '24', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('95', '5', '25', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('96', '5', '26', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('97', '5', '27', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('98', '5', '28', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_menu_relation` VALUES ('99', '5', '29', '2020-01-01 01:01:01', '2020-01-01 01:01:01');

-- ----------------------------
-- Records of admin_role_resource_relation
-- ----------------------------
INSERT INTO `admin_role_resource_relation` VALUES ('165', '5', '25', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('166', '5', '26', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('167', '5', '27', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('168', '5', '28', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('169', '5', '29', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('170', '5', '30', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
INSERT INTO `admin_role_resource_relation` VALUES ('171', '5', '31', '2020-01-01 01:01:01', '2020-01-01 01:01:01');
