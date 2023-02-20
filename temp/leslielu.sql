/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : leslielu

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/02/2023 21:20:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名称',
  `score` int NULL DEFAULT NULL COMMENT '学分',
  `times` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课时间',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '是否开课',
  `teacher_id` int NULL DEFAULT NULL COMMENT '授课老师id',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除 0为未删除 1为已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '此表已无用' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '大学物理', 5, '40', 0, 12, 0);
INSERT INTO `course` VALUES (2, '高等数学', 10, '45', 0, 13, 0);
INSERT INTO `course` VALUES (3, '大学英语', 7, '30', NULL, 13, 0);
INSERT INTO `course` VALUES (4, '数据结构', 10, '60', 1, 13, 0);

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`student_id`, `course_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '此表已无用' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (2, 1);
INSERT INTO `student_course` VALUES (26, 2);
INSERT INTO `student_course` VALUES (26, 3);
INSERT INTO `student_course` VALUES (27, 2);

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`  (
  `id` int NOT NULL COMMENT '台帐管理id',
  `event_id` int NULL DEFAULT NULL COMMENT '资产管理中的事件id',
  `user_id` int NULL DEFAULT NULL COMMENT '当前用户的id',
  `admin_id` int NULL DEFAULT NULL COMMENT '审批人的id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '事件处理完毕的时间',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除 0为未删除 1为已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES (1, 1, 2, NULL, '2023-02-17 10:47:42', 0);

-- ----------------------------
-- Table structure for sys_assets
-- ----------------------------
DROP TABLE IF EXISTS `sys_assets`;
CREATE TABLE `sys_assets`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体内容',
  `purchase` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '购置流程 0为未审批 1为待审批 2为已审批',
  `accept` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '验收流程 0为未审批 1为待审批 2为已审批',
  `allocation` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '调拨流程 0为未审批 1为待审批 2为已审批',
  `management` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '处置流程 0为未审批 1为待审批 2为已审批',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除 0为未删除 1为已删除',
  `user_id` int NULL DEFAULT NULL COMMENT '事件提交用户的id',
  `price` int NULL DEFAULT NULL COMMENT '申请金额',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `admin1` int NULL DEFAULT NULL COMMENT '购置审批人的id',
  `admin2` int NULL DEFAULT NULL COMMENT '验收审批人的id',
  `admin3` int NULL DEFAULT NULL COMMENT '调拨审批人的id',
  `admin4` int NULL DEFAULT NULL COMMENT '处置审批人的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_assets
-- ----------------------------
INSERT INTO `sys_assets` VALUES (1, '购置申请', '购买电脑一台', 2, 2, 2, 2, 0, 13, 1000, '2023-12-16 16:38:15', 2, 20, 21, 3);
INSERT INTO `sys_assets` VALUES (2, '购置请求', '购买一台平板', 2, 1, 0, 0, 0, 2, 2000, '2023-02-16 16:38:15', 21, NULL, NULL, NULL);
INSERT INTO `sys_assets` VALUES (3, '申请', '需要两颗螺丝钉', 2, 2, 2, 1, 0, 2, 3000, '2023-02-16 16:38:15', 2, 3, 21, 21);
INSERT INTO `sys_assets` VALUES (4, '申请', '买一台等离子电视', 2, 2, 2, 2, 0, 12, 400, '2023-02-16 16:38:15', 2, 3, 2, 21);
INSERT INTO `sys_assets` VALUES (5, '111', '111', 1, 0, 0, 0, 0, 12, 5000, '2023-02-16 16:39:11', NULL, NULL, NULL, NULL);
INSERT INTO `sys_assets` VALUES (6, '请求', '想要买3060ti', 1, 0, 0, 0, 0, 13, 5000, '2023-02-16 17:00:21', NULL, NULL, NULL, NULL);
INSERT INTO `sys_assets` VALUES (7, '11', '11', 2, 1, 0, 0, 0, 2, 7000, '2023-02-16 17:08:36', 20, NULL, NULL, NULL);
INSERT INTO `sys_assets` VALUES (8, '123', '123', 2, 1, 0, 0, 0, 2, 1000, '2023-02-16 17:22:49', 21, NULL, NULL, NULL);
INSERT INTO `sys_assets` VALUES (9, '5678', '5678', 2, 2, 1, 0, 0, 4, 6650, '2023-02-16 17:30:22', 21, 3, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('user', 'el-icon-user', 'icon');
INSERT INTO `sys_dict` VALUES ('house', 'el-icon-house', 'icon');
INSERT INTO `sys_dict` VALUES ('menu', 'el-icon-menu', 'icon');
INSERT INTO `sys_dict` VALUES ('custom', 'el-icon-s-custom', 'icon');
INSERT INTO `sys_dict` VALUES ('time', 'el-icon-time', 'icon');
INSERT INTO `sys_dict` VALUES ('document', 'el-icon-document', 'icon');
INSERT INTO `sys_dict` VALUES ('grid', 'el-icon-s-grid', 'icon');
INSERT INTO `sys_dict` VALUES ('help', 'el-icon-help', 'icon');
INSERT INTO `sys_dict` VALUES ('picture', 'el-icon-picture-outline-round', 'icon');
INSERT INTO `sys_dict` VALUES ('camera', 'el-icon-camera\r\n', 'icon');
INSERT INTO `sys_dict` VALUES ('bell', 'el-icon-bell', 'icon');
INSERT INTO `sys_dict` VALUES ('promotion', 'el-icon-s-promotion', 'icon');
INSERT INTO `sys_dict` VALUES ('shop', 'el-icon-s-shop\r\n', 'icon');
INSERT INTO `sys_dict` VALUES ('flag', 'el-icon-s-flag', 'icon');
INSERT INTO `sys_dict` VALUES ('basketball', 'el-icon-basketball', 'icon');
INSERT INTO `sys_dict` VALUES ('football', 'el-icon-football\r\n', 'icon');
INSERT INTO `sys_dict` VALUES ('soccer', 'el-icon-soccer', 'icon');
INSERT INTO `sys_dict` VALUES ('baseball', 'el-icon-baseball', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小（KB）',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下载链接',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '假删除 1为“已删除” 0为“未删除”',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用链接 1“可用”  0“不可用”',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件md5',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (10, 'angel.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (11, '675573ec00224d90aca5c58536bd3cb5.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 0, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (12, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 0, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (13, '19109031039 陆宇鹏 固定资产管理系统的设计与实现 毕业论文(设计) 开题报告.doc', 'doc', 141, 'http://localhost:9090/file/8b3459afc5434b57a0128c19f531a6d2.doc', 0, 0, '4ef1c22297e20afc618204b0461c5698');
INSERT INTO `sys_file` VALUES (14, '675573ec00224d90aca5c58536bd3cb5.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (15, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (16, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (17, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (18, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (19, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (20, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (21, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (22, '675573ec00224d90aca5c58536bd3cb5.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (23, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (24, 'angel.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (25, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (26, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (27, 'angel.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (28, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (29, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (30, '258.jpg', 'jpg', 156, 'http://localhost:9090/file/ce412d8bca3b406887914e0ad415e1a1.jpg', 0, 1, '33effafbe1b7f5727edfc96a18468f08');
INSERT INTO `sys_file` VALUES (31, '8a61176a0a02c21a02696c347199be4f.jpg', 'jpg', 329, 'http://localhost:9090/file/a96087f0ff354c328660bb1a0ade80fc.jpg', 0, 1, '2d422ae68c4864129aca6b0a91893635');
INSERT INTO `sys_file` VALUES (32, 'e054aa2ec7b247218f47683401a827c7.PNG', 'PNG', 4, 'http://localhost:9090/file/d74703282b814107a2f6334ca58690e2.PNG', 0, 1, 'f45ad92dbe44373910ead48a5988d257');
INSERT INTO `sys_file` VALUES (33, '8a61176a0a02c21a02696c347199be4f.jpg', 'jpg', 810, 'http://localhost:9090/file/d55b54251fad4fbeb6986156f2a9fa74.jpg', 0, 1, '62e04bb1c9c6348c1d07b1d0a530e6b7');
INSERT INTO `sys_file` VALUES (34, 'angel.jpg', 'jpg', 399, 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 1, 'c56c9dd1104eec2ad6a643629b48b350');
INSERT INTO `sys_file` VALUES (35, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (36, '英语.docx', 'docx', 17, 'http://localhost:9090/file/1bd217bbc7bd423b886d5a8c6c1085ba.docx', 0, 1, '9b66e5ae156e6fdc5d450d8b419b710d');
INSERT INTO `sys_file` VALUES (37, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (38, '19园林1班.xlsx', 'xlsx', 12, 'http://localhost:9090/file/49377505aa5c4272a732dec6872d80b9.xlsx', 0, 1, 'f8d3fb0ee779c4735b79a04733db191b');
INSERT INTO `sys_file` VALUES (39, '2020-2021韶院上岸名单.xls', 'xls', 90, 'http://localhost:9090/file/4dd683eb18474944996c2149c2f24bb5.xls', 0, 1, 'ac8f5f6a596c5cfd8d99870056ef60a9');
INSERT INTO `sys_file` VALUES (40, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (41, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (42, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (43, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (44, '惠.jpeg', 'jpeg', 48, 'http://localhost:9090/file/83654db1d4ba4d9698cff6f246a9245a.jpeg', 0, 1, '7c0d89aeb709b1798c9efdbdd7fa182c');
INSERT INTO `sys_file` VALUES (45, '123.jpg', 'jpg', 124, 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 1, '7007adf2b9a38a8054461bccdf3d0438');
INSERT INTO `sys_file` VALUES (46, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (47, '28D56B4E1EA46D615B000C36C56ACEE9.jpg', 'jpg', 67, 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 1, '28d56b4e1ea46d615b000c36c56acee9');
INSERT INTO `sys_file` VALUES (48, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (49, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (50, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (51, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (52, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (53, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (54, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (55, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (56, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (57, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (58, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (59, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (60, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (61, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (62, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (63, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (64, '753.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (65, '0076EekLly1hamnddd4dkj30t40szgpf.jpg', 'jpg', 85, 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 1, '8f2c70f1a867771ffe0fccf1b95fa4f6');
INSERT INTO `sys_file` VALUES (66, '753.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (67, '花.jpg', 'jpg', 72, 'http://localhost:9090/file/0ed2621228544416b2cf72fe304f7b29.jpg', 0, 1, '019f005b7e68819d331ddec322581c85');
INSERT INTO `sys_file` VALUES (68, '753.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (69, '25aebde0e243257772b3efce9f8418f.jpg', 'jpg', 54, 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 1, 'ecf2b1886f72bcc7c0d55cee7bcf1740');
INSERT INTO `sys_file` VALUES (70, '玉子.jpg', 'jpg', 129, 'http://localhost:9090/file/ff3eb7e3448149398a17bb01f01c06dd.jpg', 0, 1, '0eeaf58f81aa96b0f1a7dc541abd083b');
INSERT INTO `sys_file` VALUES (71, '玉子.jpg', 'jpg', 129, 'http://localhost:9090/file/ff3eb7e3448149398a17bb01f01c06dd.jpg', 0, 1, '0eeaf58f81aa96b0f1a7dc541abd083b');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除 0为未删除 1为已删除',
  `pid` int NULL DEFAULT NULL COMMENT '父级id',
  `page_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '页面路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '主页', '/home', 'el-icon-house', '主页', 0, NULL, 'Home');
INSERT INTO `sys_menu` VALUES (2, '123', '123', '123', '123', 1, 1, NULL);
INSERT INTO `sys_menu` VALUES (3, '234', '234', '234', '234', 1, 1, NULL);
INSERT INTO `sys_menu` VALUES (4, '系统管理', '', 'el-icon-s-grid', '系统管理', 0, NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, '用户管理', '/user', 'el-icon-user', '用户管理', 0, 4, 'User');
INSERT INTO `sys_menu` VALUES (6, '角色管理', '/role', 'el-icon-s-custom', '角色管理', 0, 4, 'Role');
INSERT INTO `sys_menu` VALUES (7, '菜单管理', '/menu', 'el-icon-menu', '菜单管理', 0, 4, 'Menu');
INSERT INTO `sys_menu` VALUES (8, '文件管理', '/file', 'el-icon-document', '文件管理', 0, 4, 'File');
INSERT INTO `sys_menu` VALUES (9, NULL, NULL, 'el-icon-s-grid', NULL, 1, 4, NULL);
INSERT INTO `sys_menu` VALUES (10, '1', '1', NULL, '1', 1, NULL, '1');
INSERT INTO `sys_menu` VALUES (11, '1', '1', 'el-icon-user', '1', 1, 4, '1');
INSERT INTO `sys_menu` VALUES (12, '2', '2', 'el-icon-s-custom', '2', 1, NULL, '2');
INSERT INTO `sys_menu` VALUES (13, '新闻管理', '/news', 'el-icon-time', '新闻管理', 1, NULL, 'News');
INSERT INTO `sys_menu` VALUES (14, 'ssss', '/news', 'el-icon-user', '9990', 1, NULL, 'News');
INSERT INTO `sys_menu` VALUES (15, 'qqq', '/news', 'el-icon-user', 'qqq', 1, NULL, 'News');
INSERT INTO `sys_menu` VALUES (16, 'sss', '/news', 'el-icon-time', '123', 1, NULL, '/News');
INSERT INTO `sys_menu` VALUES (17, '课程管理', '/course', 'el-icon-s-custom', '课程管理', 1, NULL, 'Course');
INSERT INTO `sys_menu` VALUES (18, '业务管理', '', 'el-icon-s-shop\r\n', '固定资产业务管理', 0, NULL, '');
INSERT INTO `sys_menu` VALUES (19, '购置管理', '/purchase', 'el-icon-basketball', '购置管理', 0, 18, 'Purchase');
INSERT INTO `sys_menu` VALUES (20, '验收管理', '/accept', 'el-icon-football\r\n', '验收管理', 0, 18, 'Accept');
INSERT INTO `sys_menu` VALUES (21, '调拨管理', '/allocation', 'el-icon-soccer', '调拨管理', 0, 18, 'Allocation');
INSERT INTO `sys_menu` VALUES (22, '处置管理', '/management', 'el-icon-baseball', '处置管理', 0, 18, 'Management');
INSERT INTO `sys_menu` VALUES (23, '台帐管理', '/account', 'el-icon-s-flag', '台帐管理', 0, 4, 'Account');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `deleted` int(10) UNSIGNED ZEROFILL NULL DEFAULT 0000000000 COMMENT '逻辑删除 0为未删除 1为已删除',
  `flag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', 0000000000, 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, '普通用户', '普通用户', 0000000000, 'ROLE_USER');
INSERT INTO `sys_role` VALUES (4, '111', '111', 0000000001, '111');
INSERT INTO `sys_role` VALUES (5, '111', '111', 0000000001, '111');
INSERT INTO `sys_role` VALUES (6, '学生', '学生', 0000000001, 'ROLE_STUDENT');
INSERT INTO `sys_role` VALUES (7, '老师', '老师', 0000000001, 'ROLE_TEACHER');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (6, 1);
INSERT INTO `sys_role_menu` VALUES (6, 13);
INSERT INTO `sys_role_menu` VALUES (6, 17);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID\r\n',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除 0未删除 1已删除',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (2, 'admin', 'admin', '管理员', 'admin@qq.com', '12345678901', '广东肇庆', '2023-01-26 16:36:41', 'http://localhost:9090/file/2192d459f1304d0cab56516bc68628e3.jpg', 0, 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (3, '牛子哥', '123', '玉子', '258@qq.com', '12378945688', '广东普宁', '2023-12-26 17:17:01', 'http://localhost:9090/file/ff3eb7e3448149398a17bb01f01c06dd.jpg', 0, 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (4, 'asd', '111', 'asd', 'asd@qq.com', '12345', '广东汕头', '2023-01-28 15:24:18', 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (9, '555', '111', '555', NULL, NULL, '广东梅州', '2023-07-28 15:16:25', 'https://img0.baidu.com/it/u=49549001,1235675790&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=562', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (11, 'ert', '111', 'qwe', '147@qq.com', '14893', '广东四会', '2023-08-28 18:32:55', NULL, 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (12, 'qqq', '111', 'QCute', 'qqq@163.com', '999', '广东东莞', '2023-01-28 20:24:06', 'http://localhost:9090/file/4b4a0974186148d888302f470ae4250d.jpg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (13, 'qwe', '111', 'qwe好🐮', 'wqe', 'qwe', '上海外滩', '2023-01-28 21:00:46', 'https://ww2.sinaimg.cn/mw690/b21aa384gy1h9tryt3hpjj20nr0qo76y.jpg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (17, 'aaa', '111', '我是 aaa', 'aaa', 'aaa', '北京四合院', '2023-01-31 02:06:22', 'http://localhost:9090/file/b44f35b12738411fa0483170e7089564.jpg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (19, '258', '111', '258', '258@qq.com', '258', '广东云浮', '2023-01-31 14:21:48', 'http://localhost:9090/file/02e0d7945b3047d996621ca16d0e00fd.jpg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (20, '三六九', '369', 'sanliujiu', '369@qq.com', '369', '广东中山', '2023-01-31 14:34:26', 'https://img0.baidu.com/it/u=3723931394,3986721977&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=562', 0, 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (21, '高启强', '111', '强哥', 'gao@163.com', '14725836912', '广东韶关', '2023-01-31 14:46:50', 'http://localhost:9090/file/9b70c1c5f3434c64a8b0318976552043.jpg', 0, 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (22, '三六九', '369', 'sanliujiu', '369@qq.com', '369', '广东中山', '2023-01-31 18:57:13', '369', 1, NULL);
INSERT INTO `sys_user` VALUES (23, 'sir', '111', NULL, NULL, NULL, '上海外滩', '2023-02-01 15:28:47', NULL, 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (24, 'mandam', NULL, 'mandam', 'mandam@qq.com', '147258', '香港中环', '2023-02-01 16:08:12', NULL, 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (25, '1', NULL, '1', '1', '1', '北京四合院', '2023-02-01 16:09:01', NULL, 1, NULL);
INSERT INTO `sys_user` VALUES (26, '147', '111', '147', '369@qq.com', '369', '广东中山', '2023-02-01 16:09:54', 'http://localhost:9090/file/83654db1d4ba4d9698cff6f246a9245a.jpeg', 0, 'ROLE_USER');
INSERT INTO `sys_user` VALUES (27, '789', '789', '789', '369@qq.com', '369', '广西桂林', '2023-02-10 09:22:46', '369', 0, 'ROLE_USER');

SET FOREIGN_KEY_CHECKS = 1;
