DROP TABLE IF EXISTS `workoa_action`;
CREATE TABLE `workoa_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(32) DEFAULT '' COMMENT '操作名称',
  `action_value` varchar(32) DEFAULT '' COMMENT '模块值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;


INSERT INTO `workoa_action` VALUES ('2', '新增', 'add');
INSERT INTO `workoa_action` VALUES ('4', '编辑', 'edit');
INSERT INTO `workoa_action` VALUES ('6', '删除', 'delete');
INSERT INTO `workoa_action` VALUES ('7', '查询', 'select');
INSERT INTO `workoa_action` VALUES ('11', '视图', 'view');
INSERT INTO `workoa_action` VALUES ('18', '角色权限视图', 'rolePermitView');
INSERT INTO `workoa_action` VALUES ('19', '添加角色权限', 'rolePermitAdd');
INSERT INTO `workoa_action` VALUES ('20', '删除角色权限', 'rolePermitDelete');
INSERT INTO `workoa_action` VALUES ('21', '角色用户视图', 'roleUserView');
INSERT INTO `workoa_action` VALUES ('22', '角色用户查询', 'roleUserSelect');
INSERT INTO `workoa_action` VALUES ('23', '添加角色用户', 'roleUserAdd');
INSERT INTO `workoa_action` VALUES ('24', '删除角色用户', 'roleUserDelete');
INSERT INTO `workoa_action` VALUES ('25', '审核', 'check');
INSERT INTO `workoa_action` VALUES ('26', '部门经理审核', 'departCheck');
INSERT INTO `workoa_action` VALUES ('27', '总经理审核', 'generalCheck');
INSERT INTO `workoa_action` VALUES ('28', '员工管理员审核', 'staffCheck');

DROP TABLE IF EXISTS `workoa_company`;
CREATE TABLE `workoa_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(32) DEFAULT '' COMMENT '公司名称',
  `is_del` tinyint(4) DEFAULT '1' COMMENT '是否删除：0-删除，1-未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_company` VALUES ('1', '测试集团', '1');

DROP TABLE IF EXISTS `workoa_department`;
CREATE TABLE `workoa_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_code` varchar(3) DEFAULT '' COMMENT '部门编码',
  `department_desc` varchar(100) DEFAULT '' COMMENT '部门描述',
  `department_name` varchar(20) DEFAULT '' COMMENT '部门名称',
  `company_id` int(11) DEFAULT '0' COMMENT '公司id',
  `super_id` int(11) DEFAULT '0' COMMENT '上级id',
  `super_code` varchar(3) DEFAULT '' COMMENT '上级部门编码',
  `is_del` tinyint(4) DEFAULT '1' COMMENT '是否删除：1-否，0-删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_department_code` (`department_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;


INSERT INTO `workoa_department` VALUES ('20', '001', '史蒂芬森地方', '研发', '1', '0', null, '1');
INSERT INTO `workoa_department` VALUES ('21', '002', '测试1123大师傅但是sdfsd ', '支付组', '1', '20', '001', '1');
INSERT INTO `workoa_department` VALUES ('22', '004', '', '任务组', '1', '20', '001', '1');
INSERT INTO `workoa_department` VALUES ('23', '003', '', '用户组', '1', '20', '001', '1');
INSERT INTO `workoa_department` VALUES ('24', '006', '', 'test', '1', '0', null, '0');
INSERT INTO `workoa_department` VALUES ('25', '007', '', 'test1', '1', '24', '006', '0');


DROP TABLE IF EXISTS `workoa_flow_process`;
CREATE TABLE `workoa_flow_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `process_key` varchar(50) NOT NULL DEFAULT '' COMMENT '流程key',
  `process_name` varchar(50) NOT NULL DEFAULT '' COMMENT '流程名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_module`;
CREATE TABLE `workoa_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(32) DEFAULT '' COMMENT '模块名',
  `module_desc` varchar(100) DEFAULT '' COMMENT '模块描述',
  `link_url` varchar(200) DEFAULT '' COMMENT '访问路径',
  `super_id` int(11) DEFAULT '0' COMMENT '上级模块id',
  `module_value` varchar(32) DEFAULT '' COMMENT '模块值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(4) DEFAULT '1' COMMENT '是否删除：1-未删除0-删除',
  `is_parent` tinyint(4) DEFAULT '0' COMMENT '是否是父节点：0-否1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_module` VALUES ('11', '部门管理', '管理部门、添加、编辑、删除部门', '/depart/index.html', '16', 'depart', '2014-08-17 16:53:10', '1', '0');
INSERT INTO `workoa_module` VALUES ('12', '员工管理', '', '/user/index.html', '16', 'employee', '2014-08-18 08:48:39', '1', '0');
INSERT INTO `workoa_module` VALUES ('13', '角色管理', '', '/role/index.html', '16', 'role', '2014-08-18 08:56:12', '1', '0');
INSERT INTO `workoa_module` VALUES ('16', '系统管理', '', '', '0', 'system', '2014-08-18 15:54:35', '1', '1');
INSERT INTO `workoa_module` VALUES ('20', '模块管理', '', '/module/index.html', '16', 'module', '2014-09-03 17:19:22', '1', '0');
INSERT INTO `workoa_module` VALUES ('26', 'Action管理', '', '/action/index.html', '16', 'action', '2014-09-09 17:05:38', '1', '0');
INSERT INTO `workoa_module` VALUES ('27', '日常工作管理', '', '', '0', 'routine', '2014-10-14 13:46:59', '1', '1');
INSERT INTO `workoa_module` VALUES ('28', '工作日历', '用于添加私人事件和工作内容，还可以定时提醒', '/schedule/index.html', '27', 'calendar', '2014-10-14 13:48:33', '1', '0');
INSERT INTO `workoa_module` VALUES ('29', '工作日程审核', '', '/schedule/more.html', '27', 'schedule', '2014-10-15 15:29:29', '1', '0');
INSERT INTO `workoa_module` VALUES ('30', '工作流程', '', '', '0', 'workflow', '2014-10-29 11:40:51', '1', '1');
INSERT INTO `workoa_module` VALUES ('31', '加班申请', '', '/overtime/index.html', '30', 'overtime', '2014-10-29 11:41:27', '1', '0');
INSERT INTO `workoa_module` VALUES ('32', '我的流程管理', '', '/processManage/index.html', '27', 'task', '2014-11-06 12:15:06', '1', '0');
INSERT INTO `workoa_module` VALUES ('33', '流程配置', '', '/processConfigure/index.html', '16', 'processConfigure', '2014-11-06 15:24:08', '1', '0');
INSERT INTO `workoa_module` VALUES ('34', '流程控制中心', '', '/processManage/manageCenter.html', '16', 'manageCenter', '2014-11-18 15:41:23', '1', '0');

DROP TABLE IF EXISTS `workoa_overtime_approve_info`;
CREATE TABLE `workoa_overtime_approve_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicant_id` int(11) NOT NULL DEFAULT '0' COMMENT '申请人id',
  `applicant_name` varchar(50) NOT NULL DEFAULT '' COMMENT '申请人用户名',
  `auditor_id` int(11) NOT NULL DEFAULT '0' COMMENT '审核人id',
  `auditor_name` varchar(50) NOT NULL DEFAULT '' COMMENT '审核人名',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '审核时间',
  `audit_content` varchar(100) DEFAULT '' COMMENT '审核内容',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态：0-不同意1-同意',
  `flow_id` int(11) NOT NULL DEFAULT '0' COMMENT '业务id',
  `process_id` varchar(100) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `task_name` varchar(50) NOT NULL DEFAULT '' COMMENT '节点名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_overtime_flow`;
CREATE TABLE `workoa_overtime_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '申请人id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `start_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `overtime_reason` varchar(200) NOT NULL DEFAULT '' COMMENT '加班理由',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-草稿1-审核中2-成功3-驳回4-失败',
  `is_del` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否删除：0-删除1-未删除',
  `hours` decimal(10,1) NOT NULL DEFAULT '0.0' COMMENT '加班时长',
  `process_id` varchar(100) NOT NULL DEFAULT '' COMMENT '流程实例id',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `super_flow_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `depart_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `depart_name` varchar(50) NOT NULL DEFAULT '' COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `workoa_permit`;
CREATE TABLE `workoa_permit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permit_code` varchar(6) NOT NULL DEFAULT '' COMMENT '权限编码',
  `module_id` int(11) NOT NULL DEFAULT '0' COMMENT '模块id',
  `action_id` int(11) NOT NULL DEFAULT '0' COMMENT 'Action id',
  `permit_value` varchar(50) NOT NULL DEFAULT '' COMMENT '权限标识',
  `permit_desc` varchar(200) DEFAULT '' COMMENT '权限描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_permit` VALUES ('34', '13_11', '13', '11', 'role:view', '角色模块可见', '2014-09-10 10:33:11');
INSERT INTO `workoa_permit` VALUES ('35', '13_7', '13', '7', 'role:select', '角色查询', '2014-09-10 10:33:28');
INSERT INTO `workoa_permit` VALUES ('36', '13_6', '13', '6', 'role:delete', '删除角色', '2014-09-10 10:33:37');
INSERT INTO `workoa_permit` VALUES ('37', '13_4', '13', '4', 'role:edit', '角色编辑', '2014-09-10 10:33:49');
INSERT INTO `workoa_permit` VALUES ('38', '13_2', '13', '2', 'role:add', '新增角色', '2014-09-10 10:34:09');
INSERT INTO `workoa_permit` VALUES ('39', '13_24', '13', '24', 'role:roleUserDelete', '删除角色用户', '2014-09-10 10:41:13');
INSERT INTO `workoa_permit` VALUES ('40', '13_23', '13', '23', 'role:roleUserAdd', '添加角色用户', '2014-09-10 10:41:28');
INSERT INTO `workoa_permit` VALUES ('41', '13_22', '13', '22', 'role:roleUserSelect', '查询角色用户', '2014-09-10 10:41:38');
INSERT INTO `workoa_permit` VALUES ('42', '13_21', '13', '21', 'role:roleUserView', '角色用户页面可见', '2014-09-10 10:41:55');
INSERT INTO `workoa_permit` VALUES ('43', '13_19', '13', '19', 'role:rolePermitAdd', '添加角色权限', '2014-09-10 10:42:09');
INSERT INTO `workoa_permit` VALUES ('44', '13_20', '13', '20', 'role:rolePermitDelete', '删除角色权限', '2014-09-10 10:42:26');
INSERT INTO `workoa_permit` VALUES ('45', '13_18', '13', '18', 'role:rolePermitView', '角色权限页面可见', '2014-09-10 10:42:38');
INSERT INTO `workoa_permit` VALUES ('46', '20_11', '20', '11', 'module:view', '模块管理可见', '2014-09-11 16:07:05');
INSERT INTO `workoa_permit` VALUES ('47', '20_7', '20', '7', 'module:select', '模块查询', '2014-09-11 16:07:15');
INSERT INTO `workoa_permit` VALUES ('48', '20_6', '20', '6', 'module:delete', '模块删除', '2014-09-11 16:07:24');
INSERT INTO `workoa_permit` VALUES ('49', '20_2', '20', '2', 'module:add', '模块新增', '2014-09-11 16:07:35');
INSERT INTO `workoa_permit` VALUES ('50', '20_4', '20', '4', 'module:edit', '编辑模块', '2014-09-11 16:07:44');
INSERT INTO `workoa_permit` VALUES ('51', '11_11', '11', '11', 'depart:view', '部门管理可见', '2014-09-11 16:12:22');
INSERT INTO `workoa_permit` VALUES ('52', '11_6', '11', '6', 'depart:delete', '部门删除', '2014-09-11 16:12:46');
INSERT INTO `workoa_permit` VALUES ('53', '11_4', '11', '4', 'depart:edit', '编辑部门', '2014-09-11 16:12:57');
INSERT INTO `workoa_permit` VALUES ('54', '11_2', '11', '2', 'depart:add', '添加部门', '2014-09-11 16:13:06');
INSERT INTO `workoa_permit` VALUES ('55', '12_11', '12', '11', 'employee:view', '员工模块可见', '2014-09-11 16:23:27');
INSERT INTO `workoa_permit` VALUES ('56', '12_7', '12', '7', 'employee:select', '员工查询', '2014-09-11 16:23:40');
INSERT INTO `workoa_permit` VALUES ('58', '12_4', '12', '4', 'employee:edit', '编辑员工', '2014-09-11 16:24:19');
INSERT INTO `workoa_permit` VALUES ('59', '12_2', '12', '2', 'employee:add', '添加员工', '2014-09-11 16:24:44');
INSERT INTO `workoa_permit` VALUES ('60', '26_11', '26', '11', 'action:view', 'action管理可见', '2014-09-11 16:26:09');
INSERT INTO `workoa_permit` VALUES ('61', '26_7', '26', '7', 'action:select', 'action查询', '2014-09-11 16:26:18');
INSERT INTO `workoa_permit` VALUES ('62', '26_6', '26', '6', 'action:delete', 'action删除', '2014-09-11 16:26:26');
INSERT INTO `workoa_permit` VALUES ('63', '26_4', '26', '4', 'action:edit', 'action编辑', '2014-09-11 16:26:36');
INSERT INTO `workoa_permit` VALUES ('64', '26_2', '26', '2', 'action:add', '添加action', '2014-09-11 16:26:46');
INSERT INTO `workoa_permit` VALUES ('65', '11_7', '11', '7', 'depart:select', '查询部门', '2014-09-11 16:30:06');
INSERT INTO `workoa_permit` VALUES ('66', '20_21', '20', '21', 'module:roleUserView', '模块角色用户视图', '2014-10-09 17:17:22');
INSERT INTO `workoa_permit` VALUES ('71', '33_2', '33', '2', 'processConfigure:add', '新增流程配置', '2014-11-14 16:11:13');
INSERT INTO `workoa_permit` VALUES ('72', '33_4', '33', '4', 'processConfigure:edit', '编辑流程配置', '2014-11-14 16:11:25');
INSERT INTO `workoa_permit` VALUES ('73', '33_6', '33', '6', 'processConfigure:delete', '删除流程配置', '2014-11-14 16:11:36');
INSERT INTO `workoa_permit` VALUES ('74', '33_7', '33', '7', 'processConfigure:select', '查询流程配置', '2014-11-14 16:11:56');
INSERT INTO `workoa_permit` VALUES ('75', '33_11', '33', '11', 'processConfigure:view', '流程配置可见', '2014-11-14 16:12:08');
INSERT INTO `workoa_permit` VALUES ('77', '31_28', '31', '28', 'overtime:staffCheck', '员工管理员审核', '2014-11-17 17:13:47');
INSERT INTO `workoa_permit` VALUES ('78', '31_27', '31', '27', 'overtime:generalCheck', '总经理审核', '2014-11-17 17:13:57');
INSERT INTO `workoa_permit` VALUES ('79', '31_26', '31', '26', 'overtime:departCheck', '部门经理审核', '2014-11-17 17:14:09');
INSERT INTO `workoa_permit` VALUES ('80', '29_25', '29', '25', 'schedule:check', '工作日程审核', '2014-11-18 14:34:11');
INSERT INTO `workoa_permit` VALUES ('81', '34_11', '34', '11', 'manageCenter:view', '流程管理中心可见', '2014-11-19 16:59:38');
INSERT INTO `workoa_permit` VALUES ('82', '34_7', '34', '7', 'manageCenter:select', '流程管理中心查询', '2014-11-19 16:59:55');
INSERT INTO `workoa_permit` VALUES ('83', '34_4', '34', '4', 'manageCenter:edit', '流程修改', '2014-11-19 17:00:13');

DROP TABLE IF EXISTS `workoa_permit_group`;
CREATE TABLE `workoa_permit_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(32) DEFAULT '',
  `group_code` varchar(3) DEFAULT '',
  `permit_code` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `workoa_position`;
CREATE TABLE `workoa_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_name` varchar(32) DEFAULT '',
  `position_desc` varchar(100) DEFAULT '',
  `position_code` varchar(3) DEFAULT '',
  `dept_code` varchar(3) DEFAULT '',
  `super_code` varchar(3) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `workoa_position_permit`;
CREATE TABLE `workoa_position_permit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_code` varchar(3) DEFAULT '',
  `permit_code` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_position_user`;
CREATE TABLE `workoa_position_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_code` varchar(3) DEFAULT '',
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_process_configure`;
CREATE TABLE `workoa_process_configure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `process_key` varchar(100) NOT NULL DEFAULT '' COMMENT '流程key',
  `process_name` varchar(100) NOT NULL DEFAULT '' COMMENT '流程名',
  `task_name` varchar(100) NOT NULL DEFAULT '' COMMENT '任务节点名',
  `task_desc` varchar(200) DEFAULT '' COMMENT '任务节点描述',
  `task_deal_url` varchar(100) NOT NULL DEFAULT '' COMMENT '任务节点处理url',
  `create_time` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_process_configure_processkey` (`process_key`,`task_name`) USING BTREE,
  KEY `idx_process_configure_processkey_taskname` (`process_key`,`task_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_process_configure` VALUES ('1', 'overtime', '加班', 'departManagerApprove', '部门经理审批', '/overtime/toDeaprtApprove.html', '2014-11-06 15:56:53');
INSERT INTO `workoa_process_configure` VALUES ('4', 'overtime', '加班', 'generalManagerApprove', '总经理审核', '/overtime/toGeneralApprove.html', '2014-11-06 16:30:01');
INSERT INTO `workoa_process_configure` VALUES ('5', 'overtime', '加班', 'staffOvertimeHourSummary', '员工加班汇总', '/overtime/toStaffSummary.html', '2014-11-06 16:31:57');
INSERT INTO `workoa_process_configure` VALUES ('6', 'overtime', '加班', 'moidfy', '申请人退回修改', '/overtime/toRefuseModify.html', '2014-11-06 16:32:26');
INSERT INTO `workoa_process_configure` VALUES ('7', 'overtime', '加班申请', 'info', '任务详情', '/overtime/toOvertimeFlowInfo.html', '2014-11-27 19:47:09');


DROP TABLE IF EXISTS `workoa_role`;
CREATE TABLE `workoa_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_value` varchar(32) DEFAULT '' COMMENT '角色值',
  `role_desc` varchar(100) DEFAULT '' COMMENT '角色描述',
  `is_del` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否删除：1-未删除0-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_rolename` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_role` VALUES ('1', 'superManager', null, '超级管理员', '1', '2014-08-08 03:52:07');
INSERT INTO `workoa_role` VALUES ('9', 'roleManager', null, '角色管理员', '1', '2014-09-01 04:12:02');
INSERT INTO `workoa_role` VALUES ('11', 'test1', '\'\'', 'test12', '0', '2014-09-04 04:02:04');
INSERT INTO `workoa_role` VALUES ('12', 'moduleManager', '\'\'', '模块管理员', '1', '2014-09-11 04:07:58');
INSERT INTO `workoa_role` VALUES ('13', 'departmentManager', '\'\'', '部门管理员', '1', '2014-09-11 04:13:18');
INSERT INTO `workoa_role` VALUES ('14', 'staffManager', '\'\'', '员工管理员', '1', '2014-09-11 04:23:02');
INSERT INTO `workoa_role` VALUES ('15', 'actionManager', '\'\'', 'action管理员', '1', '2014-09-11 04:26:58');
INSERT INTO `workoa_role` VALUES ('17', 'test', '\'\'', '', '0', '2014-09-16 10:24:36');
INSERT INTO `workoa_role` VALUES ('18', 'scheduleManager', '\'\'', '日程审核员', '1', '2014-10-14 01:55:53');
INSERT INTO `workoa_role` VALUES ('19', 'departManager', '\'\'', '部门经理', '1', '2014-11-04 05:10:10');
INSERT INTO `workoa_role` VALUES ('20', 'generalManager', '\'\'', '总经理', '1', '2014-11-05 09:58:46');
INSERT INTO `workoa_role` VALUES ('21', 'processConfigureManager', '\'\'', '流程配置管理员', '1', '2014-11-14 04:12:57');
INSERT INTO `workoa_role` VALUES ('22', 'processController', '\'\'', '流程控制中心管理员', '1', '2014-11-19 05:04:12');

DROP TABLE IF EXISTS `workoa_role_permit`;
CREATE TABLE `workoa_role_permit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `permit_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

INSERT INTO `workoa_role_permit` VALUES ('25', '9', '38');
INSERT INTO `workoa_role_permit` VALUES ('26', '9', '37');
INSERT INTO `workoa_role_permit` VALUES ('29', '9', '34');
INSERT INTO `workoa_role_permit` VALUES ('31', '9', '44');
INSERT INTO `workoa_role_permit` VALUES ('32', '9', '43');
INSERT INTO `workoa_role_permit` VALUES ('34', '9', '41');
INSERT INTO `workoa_role_permit` VALUES ('35', '9', '40');
INSERT INTO `workoa_role_permit` VALUES ('36', '9', '39');
INSERT INTO `workoa_role_permit` VALUES ('42', '12', '50');
INSERT INTO `workoa_role_permit` VALUES ('43', '12', '48');
INSERT INTO `workoa_role_permit` VALUES ('44', '12', '47');
INSERT INTO `workoa_role_permit` VALUES ('45', '12', '46');
INSERT INTO `workoa_role_permit` VALUES ('46', '12', '49');
INSERT INTO `workoa_role_permit` VALUES ('47', '13', '54');
INSERT INTO `workoa_role_permit` VALUES ('48', '13', '53');
INSERT INTO `workoa_role_permit` VALUES ('49', '13', '52');
INSERT INTO `workoa_role_permit` VALUES ('50', '13', '51');
INSERT INTO `workoa_role_permit` VALUES ('51', '14', '59');
INSERT INTO `workoa_role_permit` VALUES ('52', '14', '58');
INSERT INTO `workoa_role_permit` VALUES ('53', '14', '56');
INSERT INTO `workoa_role_permit` VALUES ('54', '14', '55');
INSERT INTO `workoa_role_permit` VALUES ('55', '15', '64');
INSERT INTO `workoa_role_permit` VALUES ('56', '15', '63');
INSERT INTO `workoa_role_permit` VALUES ('57', '15', '62');
INSERT INTO `workoa_role_permit` VALUES ('58', '15', '61');
INSERT INTO `workoa_role_permit` VALUES ('59', '15', '60');
INSERT INTO `workoa_role_permit` VALUES ('60', '13', '65');
INSERT INTO `workoa_role_permit` VALUES ('61', '9', '42');
INSERT INTO `workoa_role_permit` VALUES ('62', '9', '45');
INSERT INTO `workoa_role_permit` VALUES ('63', '9', '35');
INSERT INTO `workoa_role_permit` VALUES ('64', '9', '36');
INSERT INTO `workoa_role_permit` VALUES ('65', '21', '75');
INSERT INTO `workoa_role_permit` VALUES ('66', '21', '74');
INSERT INTO `workoa_role_permit` VALUES ('67', '21', '73');
INSERT INTO `workoa_role_permit` VALUES ('68', '21', '72');
INSERT INTO `workoa_role_permit` VALUES ('69', '21', '71');
INSERT INTO `workoa_role_permit` VALUES ('73', '19', '79');
INSERT INTO `workoa_role_permit` VALUES ('76', '20', '78');
INSERT INTO `workoa_role_permit` VALUES ('77', '14', '77');
INSERT INTO `workoa_role_permit` VALUES ('78', '18', '80');
INSERT INTO `workoa_role_permit` VALUES ('79', '22', '83');
INSERT INTO `workoa_role_permit` VALUES ('80', '22', '82');
INSERT INTO `workoa_role_permit` VALUES ('81', '22', '81');

DROP TABLE IF EXISTS `workoa_schedule`;
CREATE TABLE `workoa_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布者id',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '日程标题',
  `content` varchar(500) DEFAULT '' COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `update_time` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：0-待审核1-通过2-驳回3-废弃',
  `personal` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否属于个人',
  `flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否标记为重要：0-否1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_schedule_participant`;
CREATE TABLE `workoa_schedule_participant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) NOT NULL DEFAULT '0' COMMENT '日程id',
  `participator_id` int(11) NOT NULL DEFAULT '0' COMMENT '参与者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_shortcut_module`;
CREATE TABLE `workoa_shortcut_module` (
  `id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `module_id` int(11) NOT NULL DEFAULT '0' COMMENT '模块id',
  `order` int(5) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `workoa_user`;
CREATE TABLE `workoa_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `code` varchar(5) NOT NULL DEFAULT '' COMMENT '人员编码',
  `email` varchar(30) NOT NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '手机',
  `telphone` varchar(20) DEFAULT '' COMMENT '固话',
  `address` varchar(100) DEFAULT '' COMMENT '地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 1:在职2：离职3：实习4：试用期',
  `image_key` varchar(10) DEFAULT '' COMMENT '用户头像',
  `dept_code` varchar(3) NOT NULL DEFAULT '' COMMENT '部门编码',
  `real_name` varchar(10) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `update_time` datetime DEFAULT NULL COMMENT '编辑时间',
  `is_del` tinyint(4) DEFAULT '1' COMMENT '是否删除：1-否，0-删除',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门id',
  `salt` varchar(100) DEFAULT '' COMMENT '密码盐',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_unique_username` (`user_name`) USING BTREE,
  UNIQUE KEY `index_user_unique_code` (`code`) USING BTREE,
  UNIQUE KEY `index_user_unique_email` (`email`) USING BTREE,
  KEY `index_user_username` (`user_name`) USING BTREE,
  KEY `index_user_code` (`code`) USING BTREE,
  KEY `index_user_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;


INSERT INTO `workoa_user` VALUES ('1', 'admin', '2916f2608b8e0b04dfb3f5b6c7f5b429', '00000', 'admin@126.com', '15996270364', null, null, '2013-10-15 13:38:03', '1', '1', '001', 'admin', '2014-09-11 17:27:19', '1', '20', 'bb4dc73f09f21e14223a692afaa4ad52');
INSERT INTO `workoa_user` VALUES ('64', 'dengmawei', '140c815786e662d36ff0b395fa7a16ac', '00001', 'dengmawei@126.com', '15996270364', '', '', '2014-08-29 15:31:11', '3', 'user-64', '004', '邓马伟', '2014-09-04 17:11:48', '1', '22', '01cc61a25c3aab77cf5af029fa2b8418');
INSERT INTO `workoa_user` VALUES ('65', 'xiaowang', 'f51ea46cfac4346378550c167650679d', '00002', 'xiaowang@126.com', '15996270363', '', '', '2014-09-16 16:42:24', '3', 'user-65', '003', '小王', '2014-09-16 16:42:24', '1', '23', '5a47eb6dce2dfbb83624df6a2ee1246c');
INSERT INTO `workoa_user` VALUES ('66', 'xiaozhang', '59cc2e7b3f8f7646cddbc2485bb41042', '00017', 'xiaozhang@126.com', '15996270367', '', '', '2014-11-20 16:42:17', '1', 'user-66', '002', '小张', '2014-11-20 16:42:17', '1', '21', '28c343e8f9dc37997bb10b017f4a23e0');
INSERT INTO `workoa_user` VALUES ('67', 'xiaoli', '76422cdb6c7289c961dbf08ec6726d32', '00018', 'xiaoli@126.com', '15996270831', '', '', '2014-11-21 14:54:26', '1', 'user-67', '004', '小李', '2014-11-21 14:54:26', '1', '22', '1c6a84fcdee7c6e6cff97eafecff22a7');

DROP TABLE IF EXISTS `workoa_user_pending_task`;
CREATE TABLE `workoa_user_pending_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `process_id` varchar(20) NOT NULL DEFAULT '' COMMENT '流程id',
  `task_id` varchar(20) NOT NULL DEFAULT '' COMMENT '任务id',
  `activity_name` varchar(50) NOT NULL DEFAULT '' COMMENT '当前活动节点名',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '处理状态：0-进行中1-完成2-挂起3失败',
  `process_key` varchar(20) NOT NULL DEFAULT '' COMMENT '流程key',
  `process_name` varchar(20) NOT NULL DEFAULT '' COMMENT '流程名',
  `sign_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '会签类型：0-竞争1-会签',
  `executor_id` int(11) DEFAULT '0' COMMENT '待办处理人id',
  PRIMARY KEY (`id`),
  KEY `idx_userpendingtask_activityname` (`activity_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `workoa_user_permit`;
CREATE TABLE `workoa_user_permit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `position_id` int(11) NOT NULL DEFAULT '0' COMMENT '职位id',
  `permit_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;


INSERT INTO `workoa_user_permit` VALUES ('18', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('19', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('20', '65', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('21', '65', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('22', '65', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('23', '65', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('24', '65', '14', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('25', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('26', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('27', '65', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('28', '64', '20', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('29', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('30', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('31', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('32', '65', '19', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('33', '64', '0', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('34', '67', '20', '0', '0');
INSERT INTO `workoa_user_permit` VALUES ('35', '64', '22', '0', '0');
