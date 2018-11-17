/*
Navicat MySQL Data Transfer

Source Server         : coonchen-local-test
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : fk

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-11-11 20:49:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_admin`
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `comment` varchar(32) DEFAULT NULL COMMENT '备注',
  `visible` smallint(6) DEFAULT NULL COMMENT '是否有效默认1是有效，0无效',
  PRIMARY KEY (`userid`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`userid`) REFERENCES `sys_user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统管理员';

-- ----------------------------
-- Records of sys_admin
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_admin_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_log`;
CREATE TABLE `sys_admin_log` (
  `salid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户日志ID',
  `sltid` int(11) NOT NULL COMMENT '日志类别ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `saldata` text COMMENT '用户日志数据',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `ipaddress` varchar(16) DEFAULT NULL COMMENT 'ip地址',
  PRIMARY KEY (`salid`),
  KEY `FK_Relationship_8` (`userid`),
  KEY `FK_Relationship_9` (`sltid`),
  CONSTRAINT `FK_Relationship_8` FOREIGN KEY (`userid`) REFERENCES `sys_admin` (`userid`),
  CONSTRAINT `FK_Relationship_9` FOREIGN KEY (`sltid`) REFERENCES `sys_log_type` (`sltid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员日志表';

-- ----------------------------
-- Records of sys_admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_admin_permission_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_permission_group`;
CREATE TABLE `sys_admin_permission_group` (
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `spgid` int(11) NOT NULL COMMENT '组ID',
  `sapgscore` bigint(20) DEFAULT NULL COMMENT '权限分值',
  PRIMARY KEY (`userid`,`spgid`),
  KEY `FK_Relationship_4` (`spgid`),
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`userid`) REFERENCES `sys_admin` (`userid`),
  CONSTRAINT `FK_Relationship_4` FOREIGN KEY (`spgid`) REFERENCES `sys_permission_group` (`spgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员权限组关系';

-- ----------------------------
-- Records of sys_admin_permission_group
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_log_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_type`;
CREATE TABLE `sys_log_type` (
  `sltid` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志类别ID',
  `sltname` varchar(32) DEFAULT NULL COMMENT '日志类别名称',
  `sltpid` int(11) DEFAULT NULL COMMENT '日志类别父ID',
  `sltpath` varchar(100) DEFAULT NULL COMMENT '日志类别路径',
  `visable` smallint(6) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`sltid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志类型';

-- ----------------------------
-- Records of sys_log_type
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `spid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `spgid` int(11) NOT NULL COMMENT '组ID',
  `spname` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `spcode` varchar(32) DEFAULT NULL COMMENT '权限代码',
  `spnum` int(11) DEFAULT NULL COMMENT '权限编号',
  PRIMARY KEY (`spid`),
  KEY `FK_Relationship_2` (`spgid`),
  CONSTRAINT `FK_Relationship_2` FOREIGN KEY (`spgid`) REFERENCES `sys_permission_group` (`spgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_permission_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_group`;
CREATE TABLE `sys_permission_group` (
  `spgid` int(11) NOT NULL AUTO_INCREMENT COMMENT '组ID',
  `spgcode` varchar(32) DEFAULT NULL COMMENT '组代码',
  `sgpname` varchar(32) DEFAULT NULL COMMENT '组名称',
  PRIMARY KEY (`spgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限组';

-- ----------------------------
-- Records of sys_permission_group
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `sugid` int(11) NOT NULL COMMENT '用户组ID',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `securekey` varchar(3) DEFAULT NULL COMMENT '密钥',
  `userheadpath` varchar(255) DEFAULT NULL COMMENT '头像',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间',
  `lastlogintime` int(11) DEFAULT NULL COMMENT '最后登录时间',
  `logintime` int(11) DEFAULT NULL COMMENT '登录次数',
  `ipaddress` varchar(16) DEFAULT NULL COMMENT 'ip地址',
  `gold` double DEFAULT NULL COMMENT '积分',
  `device` varchar(32) DEFAULT NULL COMMENT '设备名称',
  `devicecode` varchar(128) DEFAULT NULL COMMENT '设备码',
  `currentversion` varchar(32) DEFAULT NULL COMMENT 'APP当前版本',
  `systemversion` varchar(32) DEFAULT NULL COMMENT '手机系统版本',
  `authentication` varchar(36) DEFAULT NULL COMMENT '身份码，最长期限30天',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信 qq等第三方登录',
  `channelid` varchar(128) DEFAULT NULL COMMENT '通道号(小米推送)',
  `loginsource` smallint(6) DEFAULT NULL COMMENT '来源 1为QQ,2为微信',
  `vu` smallint(6) DEFAULT NULL COMMENT '虚拟用户',
  `apksource` varchar(255) DEFAULT NULL COMMENT '应用来源',
  `contactmobile` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `realname` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `visable` smallint(6) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`userid`),
  KEY `FK_Relationship_7` (`sugid`),
  CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`sugid`) REFERENCES `sys_user_group` (`sugid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('5', '1', 'coonchen', '15524703078', 'aaaf8d7a316ca4ba905241b47e80bd19', 'X63', null, '1505669877', '0', '0', '0:0:0:0:0:0:0:1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for `sys_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `sugid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户组ID',
  `sugname` varchar(32) DEFAULT NULL COMMENT '用户组名称',
  PRIMARY KEY (`sugid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES ('1', '普通用户');

-- ----------------------------
-- Table structure for `sys_user_group_permission_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group_permission_group`;
CREATE TABLE `sys_user_group_permission_group` (
  `sugid` int(11) NOT NULL COMMENT '用户组ID',
  `spgid` int(11) NOT NULL COMMENT '组ID',
  `sugpgscore` bigint(20) DEFAULT NULL COMMENT '权限分值',
  PRIMARY KEY (`sugid`,`spgid`),
  KEY `FK_Relationship_6` (`spgid`),
  CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`sugid`) REFERENCES `sys_user_group` (`sugid`),
  CONSTRAINT `FK_Relationship_6` FOREIGN KEY (`spgid`) REFERENCES `sys_permission_group` (`spgid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组权限组关系';

-- ----------------------------
-- Records of sys_user_group_permission_group
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_variable`
-- ----------------------------
DROP TABLE IF EXISTS `sys_variable`;
CREATE TABLE `sys_variable` (
  `variableid` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统变量',
  `vname` varchar(32) DEFAULT NULL COMMENT '变量名',
  `vvalue` text COMMENT '变量值',
  `setfrontvar` smallint(6) DEFAULT NULL COMMENT '是否放入前端模板变量',
  `vdiscretion` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`variableid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统变量';

-- ----------------------------
-- Records of sys_variable
-- ----------------------------
