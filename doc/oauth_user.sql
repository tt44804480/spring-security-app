/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : zj

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-09-08 08:58:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `oauth_user`;
CREATE TABLE `oauth_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(25) DEFAULT NULL COMMENT '业务userid',
  `username` varchar(25) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `account_non_expired` char(4) DEFAULT NULL COMMENT 'true:用户没过期（可正常使用），false:用户过期（不能使用）',
  `account_non_locked` char(4) DEFAULT NULL COMMENT 'true:用户没被锁定（可正常使用），false:用户被锁定（不能使用）',
  `credentials_non_expired` char(4) DEFAULT NULL COMMENT 'true:用户登录凭证没过期（可正常使用），false:用户登录凭证过期（不能使用）',
  `enabled` char(4) DEFAULT NULL COMMENT 'true:用户可用（可正常使用），false:用户不可用（不能使用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='平台用户认证信息表';

-- ----------------------------
-- Records of oauth_user
-- ----------------------------
INSERT INTO `oauth_user` VALUES ('1', '20200901', 'wahaha', '$2a$10$SZhib6oGngUrW.7w.ev6h.W6f2LBqfIncg5c6qRMNe3UKj4IZf2hW', 'true', 'true', 'true', 'true');
