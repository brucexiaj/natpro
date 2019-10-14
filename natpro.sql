/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : natpro

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2019-10-14 21:01:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `phonenumber` varchar(11) NOT NULL,
  `correct` int(2) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gift` varchar(10) NOT NULL DEFAULT '青铜',
  `libao` varchar(30) NOT NULL DEFAULT '未选择礼包',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('0000000001', '18366116789', '12', '0000-00-00 00:00:00', '2', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000002', '18366116789', '12', '0000-00-00 00:00:00', '2', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000003', '18366116789', '12', '0000-00-00 00:00:00', '2', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000004', '18366116789', '12', '2019-10-02 21:53:27', '2', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000005', 'null', '23', '2019-10-02 21:58:31', '1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000006', '18477777777', '23', '2019-10-02 22:05:44', '1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000007', '18477777777', '23', '2019-10-02 22:31:10', '1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000008', '18477777777', '23', '2019-10-03 18:17:11', '1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000009', '235435', '0', '2019-10-04 01:40:21', '0', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000010', '15658083720', '0', '2019-10-10 00:16:12', '0', '托福/雅思核心词汇书');
INSERT INTO `userinfo` VALUES ('0000000011', '17788888888', '8', '2019-10-04 16:24:34', '0', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000012', '15658083720', '7', '2019-10-10 00:16:12', '青铜', '托福/雅思核心词汇书');
INSERT INTO `userinfo` VALUES ('0000000013', '-1', '-1', '2019-10-09 23:33:11', '-1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000014', '15658083720', '5', '2019-10-10 00:16:12', '青铜', '托福/雅思核心词汇书');
INSERT INTO `userinfo` VALUES ('0000000015', '-1', '-1', '2019-10-10 00:11:57', '-1', '未选择礼包');
INSERT INTO `userinfo` VALUES ('0000000016', '15658083720', '8', '2019-10-10 00:16:12', '青铜', '托福/雅思核心词汇书');
