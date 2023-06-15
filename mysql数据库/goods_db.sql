/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : goods_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2017-12-31 23:08:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `departmentId` int(11) NOT NULL auto_increment,
  `departmentName` varchar(20) default NULL,
  `departmentType` varchar(20) default NULL,
  `departmentMemo` longtext,
  PRIMARY KEY  (`departmentId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '后勤部', '后勤管理', '管理后勤信息');
INSERT INTO `department` VALUES ('2', '采购部', '采购类', '发出采购申请');

-- ----------------------------
-- Table structure for `goodapply`
-- ----------------------------
DROP TABLE IF EXISTS `goodapply`;
CREATE TABLE `goodapply` (
  `applyId` int(11) NOT NULL auto_increment,
  `goodObj` varchar(20) default NULL,
  `applyCount` int(11) default NULL,
  `applyTime` varchar(20) default NULL,
  `personObj` varchar(20) default NULL,
  `handlePerson` varchar(20) default NULL,
  `applyMemo` longtext,
  PRIMARY KEY  (`applyId`),
  KEY `FKC7DE2091942711AE` (`goodObj`),
  KEY `FKC7DE2091B3DA7559` (`personObj`),
  CONSTRAINT `FKC7DE2091942711AE` FOREIGN KEY (`goodObj`) REFERENCES `goods` (`goodNo`),
  CONSTRAINT `FKC7DE2091B3DA7559` FOREIGN KEY (`personObj`) REFERENCES `person` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodapply
-- ----------------------------
INSERT INTO `goodapply` VALUES ('1', 'WP001', '20', '2017-12-29 11:25:21', 'ps001', '王明珠', '待审核');
INSERT INTO `goodapply` VALUES ('2', 'WP001', '10', '2017-12-31 21:57:51', 'ps001', '王佳俊', '审核通过');

-- ----------------------------
-- Table structure for `goodclass`
-- ----------------------------
DROP TABLE IF EXISTS `goodclass`;
CREATE TABLE `goodclass` (
  `goodClassId` int(11) NOT NULL auto_increment,
  `goodClassName` varchar(20) default NULL,
  PRIMARY KEY  (`goodClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodclass
-- ----------------------------
INSERT INTO `goodclass` VALUES ('1', '电脑配件');
INSERT INTO `goodclass` VALUES ('2', '办公器材');

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goodNo` varchar(20) NOT NULL,
  `goodClassObj` int(11) default NULL,
  `goodName` varchar(20) default NULL,
  `goodPhoto` varchar(50) default NULL,
  `specModel` varchar(20) default NULL,
  `measureUnit` varchar(20) default NULL,
  `stockCount` int(11) default NULL,
  `price` float default NULL,
  `totalMoney` float default NULL,
  `storeHouse` varchar(20) default NULL,
  `goodMemo` longtext,
  PRIMARY KEY  (`goodNo`),
  KEY `FK41CA73678731F35` (`goodClassObj`),
  CONSTRAINT `FK41CA73678731F35` FOREIGN KEY (`goodClassObj`) REFERENCES `goodclass` (`goodClassId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('WP001', '1', '金士顿2G内存条', 'upload/39EFB7227E77DFE1F9C248B15409190D.jpg', 'jinshidun', '条', '100', '125', '12500', '仓库1', '测试');
INSERT INTO `goods` VALUES ('WP002', '2', '轻便办公椅', 'upload/A919517B0B55DB08CDA08F39BEE48645.jpg', 'bangongyi', '个', '100', '88', '8800', '仓库2', '很经济实惠的椅子！');

-- ----------------------------
-- Table structure for `gooduse`
-- ----------------------------
DROP TABLE IF EXISTS `gooduse`;
CREATE TABLE `gooduse` (
  `useId` int(11) NOT NULL auto_increment,
  `goodObj` varchar(20) default NULL,
  `useCount` int(11) default NULL,
  `price` float default NULL,
  `totalMoney` float default NULL,
  `useTime` datetime default NULL,
  `userMan` varchar(20) default NULL,
  `operatorMan` varchar(20) default NULL,
  `storeHouse` varchar(20) default NULL,
  PRIMARY KEY  (`useId`),
  KEY `FK6F8F4F6A942711AE` (`goodObj`),
  CONSTRAINT `FK6F8F4F6A942711AE` FOREIGN KEY (`goodObj`) REFERENCES `goods` (`goodNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gooduse
-- ----------------------------
INSERT INTO `gooduse` VALUES ('1', 'WP001', '20', '120', '2400', '2017-12-29 00:00:00', '双鱼林', '王大树', '仓库1');
INSERT INTO `gooduse` VALUES ('3', 'WP001', '20', '130', '2600', '2017-12-31 00:00:00', '李小芬', '王大树', '仓库1');

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `departmentObj` int(11) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `bornDate` datetime default NULL,
  `telephone` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  PRIMARY KEY  (`user_name`),
  KEY `FK8E488775F97E0739` (`departmentObj`),
  CONSTRAINT `FK8E488775F97E0739` FOREIGN KEY (`departmentObj`) REFERENCES `department` (`departmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('ps001', '123', '2', '双鱼林', '男', '2017-12-05 00:00:00', '13539823432', '四川成都红星路');
INSERT INTO `person` VALUES ('ps002', '123', '1', '李小林', '男', '2017-12-31 00:00:00', '13539834234', '四川南充');

-- ----------------------------
-- Table structure for `purchase`
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `purchaseId` int(11) NOT NULL auto_increment,
  `goodObj` varchar(20) default NULL,
  `price` float default NULL,
  `buyCount` int(11) default NULL,
  `totalMoney` float default NULL,
  `inDate` datetime default NULL,
  `operatorMan` varchar(20) default NULL,
  `keepMan` varchar(20) default NULL,
  `storeHouse` varchar(20) default NULL,
  PRIMARY KEY  (`purchaseId`),
  KEY `FK6BC36921942711AE` (`goodObj`),
  CONSTRAINT `FK6BC36921942711AE` FOREIGN KEY (`goodObj`) REFERENCES `goods` (`goodNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchase
-- ----------------------------
INSERT INTO `purchase` VALUES ('1', 'WP001', '80', '50', '4000', '2017-12-29 00:00:00', '李明军', '王大树', '仓库1');
INSERT INTO `purchase` VALUES ('2', 'WP001', '80', '40', '3200', '2017-12-31 00:00:00', '李明文', '王佳俊', '仓库1');
