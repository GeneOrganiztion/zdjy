/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.11 : Database - zdjy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zdjy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zdjy`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `addr_province` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `addr_city` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `addr_region` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `addr_detial` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `user_id` int(11) NOT NULL,
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `address` */

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(32) NOT NULL,
  `realname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `admin_pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`username`,`password`,`realname`,`email`,`phone`,`isdelete`,`last_modified_time`,`create_time`,`admin_pid`) values (49,'admin','21232F297A57A5A743894A0E4A801FC3','chenzhangsheng12','zzugyk@163.com','18758825854',0,'2017-01-11 11:37:40','2017-01-02 22:10:13',NULL),(50,'gaoyakun','439C51A4D8456CE409CF31F195DD9C82','gaoyakun','zzugyk@163.com','13052569020',0,'2017-02-26 11:26:14','2017-02-26 11:26:14',NULL);

/*Table structure for table `assess` */

DROP TABLE IF EXISTS `assess`;

CREATE TABLE `assess` (
  `assess_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价id',
  `pro_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `assess_content` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`assess_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `assess` */

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `cart_pro_count` int(11) NOT NULL,
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  KEY `userid_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `cart` */

/*Table structure for table `classify` */

DROP TABLE IF EXISTS `classify`;

CREATE TABLE `classify` (
  `classify_id` int(11) NOT NULL AUTO_INCREMENT,
  `cla_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `cla_content` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`classify_id`),
  KEY `isdel_createtime_index` (`isdelete`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `classify` */

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_id` int(11) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `proid_index` (`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `image` */

/*Table structure for table `map_admin_permission` */

DROP TABLE IF EXISTS `map_admin_permission`;

CREATE TABLE `map_admin_permission` (
  `map_admin_perm_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `isdelete` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`map_admin_perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `map_admin_permission` */

insert  into `map_admin_permission`(`map_admin_perm_id`,`admin_id`,`permission_id`,`isdelete`,`create_time`,`last_modified_time`) values (1,49,1,0,'2017-02-24 22:07:27','2017-02-24 22:07:27'),(5,49,8,0,'2017-02-26 11:12:30','2017-02-26 11:12:30'),(6,49,9,0,'2017-02-26 11:12:42','2017-02-26 11:12:42'),(7,49,10,0,'2017-02-26 11:12:48','2017-02-26 11:12:48'),(8,49,11,0,'2017-02-26 11:12:53','2017-02-26 11:12:53'),(9,49,12,0,'2017-02-26 11:12:58','2017-02-26 11:12:58'),(10,49,13,0,'2017-02-26 11:13:04','2017-02-26 11:13:04'),(11,49,14,0,'2017-02-26 11:13:09','2017-02-26 11:13:09'),(12,49,15,0,'2017-02-26 11:13:17','2017-02-26 11:13:17'),(13,49,16,0,'2017-02-26 11:13:23','2017-02-26 11:13:23'),(14,49,17,0,'2017-02-26 11:13:32','2017-02-26 11:13:32'),(15,49,18,0,'2017-02-26 11:13:38','2017-02-26 11:13:38'),(16,49,19,0,'2017-02-26 11:13:46','2017-02-26 11:13:46'),(17,49,20,0,'2017-02-26 11:13:53','2017-02-26 11:13:53'),(18,49,20,0,'2017-02-26 11:14:18','2017-02-26 11:14:18'),(19,50,1,0,'2017-03-18 23:52:01','2017-03-18 23:52:01'),(20,50,8,0,'2017-03-18 23:52:01','2017-03-18 23:52:01');

/*Table structure for table `map_order_product` */

DROP TABLE IF EXISTS `map_order_product`;

CREATE TABLE `map_order_product` (
  `map_order_product_id` int(11) NOT NULL AUTO_INCREMENT,
  `ord_id` int(11) NOT NULL,
  `pro_id` int(11) NOT NULL,
  `pro_classify_id` int(11) NOT NULL,
  `pro_name` varchar(45) DEFAULT NULL,
  `pro_price` int(11) DEFAULT NULL,
  `pro_count` int(11) DEFAULT NULL,
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`map_order_product_id`),
  KEY `ordid_index` (`ord_id`),
  KEY `proid_index` (`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `map_order_product` */

/*Table structure for table `map_product_cart` */

DROP TABLE IF EXISTS `map_product_cart`;

CREATE TABLE `map_product_cart` (
  `map_product_cart_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `pro_size` int(11) DEFAULT NULL COMMENT '购物车中商品的尺寸',
  `pro_color` int(11) DEFAULT NULL COMMENT '购物车中的商品的颜色',
  `pro_count` int(11) DEFAULT NULL COMMENT '购物车中商品的数量',
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`map_product_cart_id`),
  KEY `proid_index` (`pro_id`),
  KEY `cartid_index` (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `map_product_cart` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `ord_num` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT '订单编号',
  `ord_state` int(10) NOT NULL COMMENT '1:未付款 2:待发货   3：待寄回 4:检测中 5:已完成',
  `ord_price` int(11) NOT NULL COMMENT '订单价格',
  `ord_pay` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '1:微信支付 2:支付宝支付 3:其他支付',
  `ord_user` int(11) NOT NULL COMMENT '下订单的用户id',
  `user_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '买家姓名',
  `user_phone` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '买家电话',
  `user_address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '买家地址',
  `user_postal` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '买家所在城市',
  `user_courier_num` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '买家寄给卖家的快递单号',
  `user_courier_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '买家寄给卖家的快递名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `prepay_id` varchar(60) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信预支付订单',
  `timestamp` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信支付时间串',
  `nonceStr` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信支付随机字符串',
  `finalsign` varchar(80) CHARACTER SET utf8 DEFAULT NULL COMMENT '微信支付生成凭证',
  `isdelete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_id`),
  KEY `ordnum_index` (`ord_num`),
  KEY `isdel_ordstate_createtime_index` (`isdelete`,`ord_state`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `orders` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `perm_name` varchar(50) NOT NULL,
  `perm_des` varchar(100) DEFAULT NULL,
  `url` varchar(45) DEFAULT NULL,
  `isdelete` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`permission_id`,`perm_name`,`perm_des`,`url`,`isdelete`,`create_time`,`last_modified_time`) values (1,'系统管理','系统管理权限','systemadmin.do',0,'2017-02-22 21:24:48','2017-02-22 21:24:48'),(8,'用户管理','系统管理-->用户管理','admin/adminPage.do',0,'2017-02-26 10:57:29','2017-02-26 10:57:29'),(9,'权限管理','系统管理-->权限管理','permission/permissionPage.do',0,'2017-02-26 11:00:36','2017-02-26 11:09:01'),(10,'用户管理','系统管理-->用户管理','mapAdminPermission/mapAdminPermissionPage.do',0,'2017-02-26 11:01:22','2017-02-26 11:01:22'),(11,'商品管理','商品管理','productManage.do',0,'2017-02-26 11:01:52','2017-02-26 11:02:42'),(12,'上传商品','商品管理-->上传商品','classify/productPage.do',0,'2017-02-26 11:03:34','2017-02-26 11:03:34'),(13,'商品查询','商品管理-->商品查询','product/productPage.do',0,'2017-02-26 11:04:27','2017-02-26 11:04:27'),(14,'商品修改 ','商品管理-->商品修改','product/productEditorPage.do',0,'2017-02-26 11:04:59','2017-02-26 11:09:08'),(15,'订单管理','订单管理','orderManage.do',0,'2017-02-26 11:05:23','2017-02-26 11:05:32'),(16,'收发货订单','订单管理-->收发货订单','orderInfo/reAndDeOrderPage.do',0,'2017-02-26 11:06:10','2017-02-26 11:09:17'),(17,'检测中订单','订单管理-->检测中订单','orderInfo/detectionOrderPage.do',0,'2017-02-26 11:06:40','2017-02-26 11:06:40'),(18,'订单管理','订单管理-->已完成订单','orderInfo/completeOrderPage.do',0,'2017-02-26 11:07:19','2017-02-26 11:07:19'),(19,'分类管理','分类管理','classifyManage.do',0,'2017-02-26 11:07:41','2017-02-26 11:07:41'),(20,'分类操作','分类管理-->分类操作','classify2/classifyListPage.do',0,'2017-02-26 11:08:11','2017-02-26 11:08:11');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `pro_head` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标题，用于补充说明',
  `product_price` int(11) NOT NULL DEFAULT '0',
  `pro_sum` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
  `pro_rateprice` int(11) DEFAULT NULL COMMENT '默认不折扣',
  `pro_online` tinyint(1) unsigned zerofill DEFAULT NULL,
  `pro_remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '默认给及一公用图片',
  `pro_detail` text COLLATE utf8_unicode_ci,
  `pro_area` varchar(256) CHARACTER SET utf8 DEFAULT 'zhejiang' COMMENT '默认浙江区域',
  `classify_id` int(11) NOT NULL,
  `isdelete` tinyint(1) unsigned DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gene_num` int(11) unsigned DEFAULT '0',
  `sel_number` int(11) unsigned DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `isdel_createtime_index` (`isdelete`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `product` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci DEFAULT '',
  `openid` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nickname` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isdelete` tinyint(1) DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `province` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `headimgurl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` tinyint(1) unsigned DEFAULT NULL,
  `gene_num` int(11) unsigned DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
