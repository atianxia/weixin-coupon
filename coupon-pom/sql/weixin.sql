/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50618
Source Host           : localhost:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50618
File Encoding         : 65001

Date: 2016-08-03 19:29:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `coupon_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `coupon_name` varchar(100) NOT NULL DEFAULT '' COMMENT '优惠券名称',
  `coupon_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '券状态： 0-待上架；1-已上架',
  `expire_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有效日期截至时间',
  `brand_logo` varchar(255) NOT NULL DEFAULT '' COMMENT '品牌标志',
  `using_rule` varchar(255) NOT NULL DEFAULT '' COMMENT '使用规则',
  `buy_link` varchar(255) NOT NULL DEFAULT '' COMMENT '购买链接',
  `amount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '优惠券用户领取次数',
  `received_times` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '领取次数',
  `is_del` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '删除标记：0-未删除；1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`),
  UNIQUE KEY `idx_coupon_id` (`coupon_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- ----------------------------
-- Table structure for coupon_item
-- ----------------------------
DROP TABLE IF EXISTS `coupon_item`;
CREATE TABLE `coupon_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `coupon_id` bigint(20) unsigned NOT NULL,
  `coupon_code` varchar(100) NOT NULL COMMENT '优惠券码',
  `user_id` varchar(100) DEFAULT '' COMMENT '用户Id',
  `receive_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '优惠券领取时间',
  `is_del` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '删除标记：0-未删除；1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_user_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券明细表';
SET FOREIGN_KEY_CHECKS=1;


DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `password` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT;

