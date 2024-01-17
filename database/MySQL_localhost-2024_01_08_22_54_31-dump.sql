-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oa
-- ------------------------------------------------------
-- Server version	5.7.33-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED='257a497e-3211-11ee-a7c9-6c240813fb00:1-160699';

--
-- Table structure for table `oa_process`
--

DROP TABLE IF EXISTS `oa_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oa_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_code` varchar(50) NOT NULL DEFAULT '' COMMENT '审批code',
  `user_id` bigint(1) NOT NULL DEFAULT '0' COMMENT '用户id',
  `process_template_id` bigint(20) DEFAULT NULL COMMENT '审批模板id',
  `process_type_id` bigint(20) DEFAULT NULL COMMENT '审批类型id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `form_values` text COMMENT '表单值',
  `process_instance_id` varchar(255) DEFAULT NULL COMMENT '流程实例id',
  `current_auditor` varchar(255) DEFAULT NULL COMMENT '当前审批人',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（0：默认 1：审批中 2：审批通过 -1：驳回）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='审批记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oa_process`
--

LOCK TABLES `oa_process` WRITE;
/*!40000 ALTER TABLE `oa_process` DISABLE KEYS */;
INSERT INTO `oa_process` VALUES (9,'1702737758105',13,1,1,'admin发起加班申请','等待test,test2,test3审批','{\"formData\":{\"sdate\":\"2023-12-16\",\"edate\":\"2023-12-16\",\"day\":\"1\",\"reason\":\"222\",\"dept\":\"产品部\"},\"formShowData\":{\"开始时间\":\"2023-12-16\",\"结束时间\":\"2023-12-16\",\"加班天数\":\"1\",\"加班原因\":\"222\",\"所属部门\":\"产品部\"}}','5baf418b-9c21-11ee-8493-da80834f07a5',NULL,1,'2023-12-16 14:42:38','2023-12-16 14:42:38',0),(10,'1702738583446',14,2,2,'zhangsan发起请假申请','审批完成（同意）','{\"formData\":{\"sdate\":\"2023-12-16\",\"edate\":\"2023-12-16\",\"day\":\"1\",\"reason\":\"寡凫单鹄\"},\"formShowData\":{\"开始时间\":\"2023-12-16\",\"结束时间\":\"2023-12-16\",\"请假天数\":\"1\",\"请假原因\":\"寡凫单鹄\"}}','479f891b-9c23-11ee-8493-da80834f07a5',NULL,2,'2023-12-16 14:56:23','2023-12-16 14:56:23',0),(14,'1702760800785',13,1,1,'admin发起加班申请','等待管理员审批','{\"formData\":{\"sdate\":\"2023-12-16\",\"edate\":\"2023-12-17\",\"day\":\"2\",\"reason\":\"经历过\",\"dept\":\"产品部\"},\"formShowData\":{\"开始时间\":\"2023-12-16\",\"结束时间\":\"2023-12-17\",\"加班天数\":\"2\",\"加班原因\":\"经历过\",\"所属部门\":\"产品部\"}}','0232698d-9c57-11ee-b12c-da80834f07a5',NULL,1,'2023-12-16 21:06:40','2023-12-16 21:06:42',0),(15,'1702760926303',13,1,1,'admin发起加班申请','等待管理员审批','{\"formData\":{\"sdate\":\"2023-12-02\",\"edate\":\"2023-12-02\",\"day\":\"1\",\"reason\":\"方式的\",\"dept\":\"产品部\"},\"formShowData\":{\"开始时间\":\"2023-12-02\",\"结束时间\":\"2023-12-02\",\"加班天数\":\"1\",\"加班原因\":\"方式的\",\"所属部门\":\"产品部\"}}','4d00a68d-9c57-11ee-b12c-da80834f07a5',NULL,1,'2023-12-16 21:08:46','2023-12-16 21:08:46',0),(27,'1702762874324',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-06\",\"edate\":\"2023-11-28\",\"day\":\"4\",\"reason\":\"Ggf\"},\"formShowData\":{\"开始时间\":\"2023-12-06\",\"结束时间\":\"2023-11-28\",\"请假天数\":\"4\",\"请假原因\":\"Ggf\"}}','d61d105b-9c5b-11ee-b12c-da80834f07a5',NULL,1,'2023-12-16 21:41:14','2023-12-16 21:41:18',0),(28,'1702763088819',13,1,1,'admin发起加班申请','等待管理员审批','{\"formData\":{\"sdate\":\"2023-12-12\",\"edate\":\"2023-12-07\",\"day\":\"4\",\"reason\":\"Ff\",\"dept\":\"产品部\"},\"formShowData\":{\"开始时间\":\"2023-12-12\",\"结束时间\":\"2023-12-07\",\"加班天数\":\"4\",\"加班原因\":\"Ff\",\"所属部门\":\"产品部\"}}','55f68e65-9c5c-11ee-b12c-da80834f07a5',NULL,1,'2023-12-16 21:44:48','2023-12-16 21:44:49',0),(30,'1702763232372',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-17\",\"edate\":\"2023-12-14\",\"day\":\"1\",\"reason\":\"Ddr\",\"dept\":\"市场部\"},\"formShowData\":{\"开始时间\":\"2023-12-17\",\"结束时间\":\"2023-12-14\",\"请假天数\":\"1\",\"请假原因\":\"Ddr\",\"所属部门\":\"市场部\"}}','ab86da75-9c5c-11ee-b12c-da80834f07a5',NULL,1,'2023-12-16 21:47:12','2023-12-16 21:47:13',0),(31,'1702764511870',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-17\",\"edate\":\"2023-12-23\",\"day\":\"1\",\"reason\":\"火狐浏览器\",\"dept\":\"市场部\"},\"formShowData\":{\"开始时间\":\"2023-12-17\",\"结束时间\":\"2023-12-23\",\"请假天数\":\"1\",\"请假原因\":\"火狐浏览器\",\"所属部门\":\"市场部\"}}','a62bf9ed-9c5f-11ee-8dec-da80834f07a5',NULL,1,'2023-12-16 22:08:31','2023-12-16 22:08:32',0),(32,'1702765634336',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-17\",\"edate\":\"2023-12-18\",\"day\":\"1\",\"reason\":\"Fc\"},\"formShowData\":{\"开始时间\":\"2023-12-17\",\"结束时间\":\"2023-12-18\",\"请假天数\":\"1\",\"请假原因\":\"Fc\"}}','4335eec7-9c62-11ee-8dec-da80834f07a5',NULL,1,'2023-12-16 22:27:14','2023-12-16 22:27:15',0),(33,'1702766590575',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-11-28\",\"edate\":\"2023-12-19\",\"day\":\"1\",\"reason\":\"Ccc\"},\"formShowData\":{\"开始时间\":\"2023-11-28\",\"结束时间\":\"2023-12-19\",\"请假天数\":\"1\",\"请假原因\":\"Ccc\"}}','7d2da536-9c64-11ee-b3e9-da80834f07a5',NULL,1,'2023-12-16 22:43:10','2023-12-16 22:43:11',0),(34,'1702772223086',14,2,2,'zhangsan发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-19\",\"edate\":\"2023-12-19\",\"day\":\"1\",\"reason\":\"Ffg\",\"dept\":\"产品部\"},\"formShowData\":{\"开始时间\":\"2023-12-19\",\"结束时间\":\"2023-12-19\",\"请假天数\":\"1\",\"请假原因\":\"Ffg\",\"所属部门\":\"产品部\"}}','9a6b6124-9c71-11ee-98fb-da80834f07a5',NULL,1,'2023-12-17 00:17:03','2023-12-17 00:17:04',0),(35,'1702775779881',13,4,3,'admin1发起旅游报销申请','审批完成（同意）','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-23\",\"2024-01-16\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":1,\"F8ya1ocmoag691\":5},\"formShowData\":{\"起止时间\":[\"2023-12-23\",\"2024-01-16\"],\"是否省内\":true,\"几人团\":1,\"满意度\":5}}','e26e2864-9c79-11ee-8b94-da80834f07a5',NULL,2,'2023-12-17 01:16:19','2023-12-17 01:16:21',0),(37,'1702824508702',13,2,2,'admin发起请假申请','等待Joanna Marks审批','{\"formData\":{\"sdate\":\"2023-12-03\",\"edate\":\"2023-12-13\",\"day\":\"11\",\"reason\":\"二夫人\",\"dept\":\"市场部\"},\"formShowData\":{\"开始时间\":\"2023-12-03\",\"结束时间\":\"2023-12-13\",\"请假天数\":\"11\",\"请假原因\":\"二夫人\",\"所属部门\":\"市场部\"}}','5711673b-9ceb-11ee-a5de-da80834f07a5',NULL,1,'2023-12-17 14:48:28','2023-12-17 14:48:29',0),(38,'1702830375899',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-23\",\"2024-02-01\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":4,\"F8ya1ocmoag691\":5},\"formShowData\":{\"起止时间\":[\"2023-12-23\",\"2024-02-01\"],\"是否省内\":true,\"几人团\":4,\"满意度\":5}}','0032e8b4-9cf9-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:26:15','2023-12-17 16:26:17',0),(39,'1702830941391',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-23\",\"2024-01-31\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":2,\"F8ya1ocmoag691\":5},\"formShowData\":{\"起止时间\":[\"2023-12-23\",\"2024-01-31\"],\"是否省内\":true,\"几人团\":2,\"满意度\":5}}','513f8bf4-9cfa-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:35:41','2023-12-17 16:35:42',0),(40,'1702831651113',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-05\",\"2024-01-10\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":3,\"F8ya1ocmoag691\":4},\"formShowData\":{\"起止时间\":[\"2023-12-05\",\"2024-01-10\"],\"是否省内\":true,\"几人团\":3,\"满意度\":4}}','f84687a4-9cfb-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:47:31','2023-12-17 16:47:31',0),(41,'1702831965950',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-16\",\"2024-01-24\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":5,\"F8ya1ocmoag691\":4},\"formShowData\":{\"起止时间\":[\"2023-12-16\",\"2024-01-24\"],\"是否省内\":true,\"几人团\":5,\"满意度\":4}}','b3f03794-9cfc-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:52:45','2023-12-17 16:52:46',0),(42,'1702832230197',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-04\",\"2024-01-23\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":-1,\"F8ya1ocmoag691\":3},\"formShowData\":{\"起止时间\":[\"2023-12-04\",\"2024-01-23\"],\"是否省内\":true,\"几人团\":-1,\"满意度\":3}}','516fe294-9cfd-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:57:10','2023-12-17 16:57:10',0),(43,'1702832396394',14,4,3,'zhangsan发起旅游报销申请','等待管理员审批','{\"formData\":{\"F5171ocmoaa97f\":[\"2023-12-16\",\"2024-01-31\"],\"F69w1ocmocqo5v\":true,\"Fwju1ocwffsogj\":4,\"F8ya1ocmoag691\":3},\"formShowData\":{\"起止时间\":[\"2023-12-16\",\"2024-01-31\"],\"是否省内\":true,\"几人团\":4,\"满意度\":3}}','b47f61e4-9cfd-11ee-ae3c-da80834f07a5',NULL,1,'2023-12-17 16:59:56','2023-12-17 16:59:57',0);
/*!40000 ALTER TABLE `oa_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oa_process_record`
--

DROP TABLE IF EXISTS `oa_process_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oa_process_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '审批流程id',
  `description` varchar(255) DEFAULT NULL COMMENT '审批描述',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态',
  `operate_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作用户id',
  `operate_user` varchar(20) DEFAULT NULL COMMENT '操作用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='审批记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oa_process_record`
--

LOCK TABLES `oa_process_record` WRITE;
/*!40000 ALTER TABLE `oa_process_record` DISABLE KEYS */;
INSERT INTO `oa_process_record` VALUES (9,9,'发起申请',1,13,'管理员','2023-12-16 14:42:38','2023-12-16 14:42:38',0),(10,9,'同意',1,13,'管理员','2023-12-16 14:53:48','2023-12-16 14:53:48',0),(11,10,'发起申请',1,14,'Brett Cremin','2023-12-16 14:56:23','2023-12-16 14:56:23',0),(12,10,'驳回',-1,14,'Brett Cremin','2023-12-16 15:04:00','2023-12-16 15:04:00',0),(13,14,'发起申请',1,13,'管理员','2023-12-16 21:06:42','2023-12-16 21:06:42',0),(14,14,'同意',1,13,'管理员','2023-12-16 21:06:53','2023-12-16 21:06:53',0),(15,15,'发起申请',1,13,'管理员','2023-12-16 21:08:46','2023-12-16 21:08:46',0),(16,15,'同意',1,13,'管理员','2023-12-16 21:08:57','2023-12-16 21:08:57',0),(17,27,'发起申请',1,13,'管理员','2023-12-16 21:41:18','2023-12-16 21:41:18',0),(18,28,'发起申请',1,13,'管理员','2023-12-16 21:44:49','2023-12-16 21:44:49',0),(19,28,'同意',1,13,'管理员','2023-12-16 21:45:11','2023-12-16 21:45:11',0),(20,30,'发起申请',1,13,'管理员','2023-12-16 21:47:13','2023-12-16 21:47:13',0),(21,31,'发起申请',1,13,'管理员','2023-12-16 22:08:32','2023-12-16 22:08:32',0),(22,32,'发起申请',1,13,'管理员','2023-12-16 22:27:15','2023-12-16 22:27:15',0),(23,33,'发起申请',1,13,'管理员','2023-12-16 22:43:11','2023-12-16 22:43:11',0),(24,33,'同意',1,14,'Brett Cremin','2023-12-16 23:08:28','2023-12-16 23:08:28',0),(25,34,'发起申请',1,14,'Brett Cremin','2023-12-17 00:17:04','2023-12-17 00:17:04',0),(26,35,'发起申请',1,13,'管理员','2023-12-17 01:16:21','2023-12-17 01:16:21',0),(27,35,'同意',1,14,'Brett Cremin','2023-12-17 01:41:25','2023-12-17 01:41:25',0),(28,35,'同意',1,13,'管理员','2023-12-17 01:55:14','2023-12-17 01:55:14',0),(29,34,'同意',1,14,'Brett Cremin','2023-12-17 14:43:34','2023-12-17 14:43:34',0),(30,32,'同意',1,14,'Brett Cremin','2023-12-17 14:43:39','2023-12-17 14:43:39',0),(31,31,'同意',1,14,'Brett Cremin','2023-12-17 14:43:44','2023-12-17 14:43:44',0),(32,30,'同意',1,14,'Brett Cremin','2023-12-17 14:43:47','2023-12-17 14:43:47',0),(33,27,'同意',1,14,'Brett Cremin','2023-12-17 14:43:52','2023-12-17 14:43:52',0),(34,37,'发起申请',1,13,'管理员','2023-12-17 14:48:29','2023-12-17 14:48:29',0),(35,37,'同意',1,14,'Brett Cremin','2023-12-17 16:17:52','2023-12-17 16:17:52',0),(36,38,'发起申请',1,14,'Brett Cremin','2023-12-17 16:26:17','2023-12-17 16:26:17',0),(37,38,'同意',1,14,'Brett Cremin','2023-12-17 16:26:23','2023-12-17 16:26:23',0),(38,39,'发起申请',1,14,'Brett Cremin','2023-12-17 16:35:42','2023-12-17 16:35:42',0),(39,39,'同意',1,14,'Brett Cremin','2023-12-17 16:35:57','2023-12-17 16:35:57',0),(40,40,'发起申请',1,14,'Brett Cremin','2023-12-17 16:47:31','2023-12-17 16:47:31',0),(41,40,'同意',1,14,'Brett Cremin','2023-12-17 16:51:21','2023-12-17 16:51:21',0),(42,41,'发起申请',1,14,'Brett Cremin','2023-12-17 16:52:46','2023-12-17 16:52:46',0),(43,41,'同意',1,14,'Brett Cremin','2023-12-17 16:53:36','2023-12-17 16:53:36',0),(44,42,'发起申请',1,14,'Brett Cremin','2023-12-17 16:57:10','2023-12-17 16:57:10',0),(45,42,'同意',1,14,'Brett Cremin','2023-12-17 16:57:16','2023-12-17 16:57:16',0),(46,43,'发起申请',1,14,'Brett Cremin','2023-12-17 16:59:57','2023-12-17 16:59:57',0),(47,43,'同意',1,14,'Brett Cremin','2023-12-17 17:00:13','2023-12-17 17:00:13',0);
/*!40000 ALTER TABLE `oa_process_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oa_process_template`
--

DROP TABLE IF EXISTS `oa_process_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oa_process_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '模板名称',
  `icon_url` varchar(100) DEFAULT NULL COMMENT '图标路径',
  `process_type_id` varchar(255) DEFAULT NULL,
  `form_props` text COMMENT '表单属性',
  `form_options` text COMMENT '表单选项',
  `process_definition_key` varchar(20) DEFAULT NULL COMMENT '流程定义key',
  `process_definition_path` varchar(255) DEFAULT NULL COMMENT '流程定义上传路径',
  `process_model_id` varchar(255) DEFAULT NULL COMMENT '流程定义模型id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='审批模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oa_process_template`
--

LOCK TABLES `oa_process_template` WRITE;
/*!40000 ALTER TABLE `oa_process_template` DISABLE KEYS */;
INSERT INTO `oa_process_template` VALUES (1,'加班','https://gw.alicdn.com/tfs/TB1bHOWCSzqK1RjSZFjXXblCFXa-112-112.png','1','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"开始时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"datePicker\",\"field\":\"edate\",\"title\":\"结束时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"day\",\"title\":\"加班天数\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"加班原因\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true}]','{\n    \"form\": {\n        \"labelPosition\": \"right\",\n        \"size\": \"medium\",\n        \"labelWidth\": \"80px\",\n        \"hideRequiredAsterisk\": false,\n        \"showMessage\": true,\n        \"inlineMessage\": false\n    },\n    \"submitBtn\": {\n                         \"innerText\":\"提交审批 \",\n                        \"round\":true,\n                        \"width\": \"280px\",\n                        \"type\":\"primary\"\n                     },\n    \"resetBtn\": false\n}\n','jiaban','process/jiaban.zip','','加班',1,'2022-12-07 06:33:51','2023-12-16 14:18:07',0),(2,'请假','https://gw.alicdn.com/imgextra/i3/O1CN01LLn0YV1LhBXs7T2iO_!!6000000001330-2-tps-120-120.png','2','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"开始时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"datePicker\",\"field\":\"edate\",\"title\":\"结束时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"day\",\"title\":\"请假天数\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"请假原因\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true}]','{\n    \"form\": {\n        \"labelPosition\": \"right\",\n        \"size\": \"medium\",\n        \"labelWidth\": \"80px\",\n        \"hideRequiredAsterisk\": false,\n        \"showMessage\": true,\n        \"inlineMessage\": false\n    },\n    \"submitBtn\": {\n                         \"innerText\":\"提交审批 \",\n                        \"round\":true,\n                        \"width\": \"280px\",\n                        \"type\":\"primary\"\n                     },\n    \"resetBtn\": false\n}\n','qingjia','process/qingjia.zip','','请假',0,'2022-12-07 07:04:02','2023-12-16 14:18:07',0),(3,'申请费用','https://gw.alicdn.com/tfs/TB1e76lCOLaK1RjSZFxXXamPFXa-112-112.png','3','[{\"type\":\"datePicker\",\"field\":\"sdate\",\"title\":\"使用时间\",\"info\":\"\",\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}],\"props\":{\"format\":\"\"}},{\"type\":\"input\",\"field\":\"amount\",\"title\":\"申请金额\",\"info\":\"\",\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"select\",\"field\":\"F0ma1n7tec860p\",\"title\":\"费用类别\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"房租费\",\"label\":\"房租费\"},{\"value\":\"水费\",\"label\":\"水费\"},{\"label\":\"电费\",\"value\":\"电费\"},{\"label\":\"网络费\",\"value\":\"网络费\"},{\"label\":\"火车票\",\"value\":\"火车票\"},{\"label\":\"飞机票\",\"value\":\"飞机票\"},{\"label\":\"部门团建费\",\"value\":\"部门团建费\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"input\",\"field\":\"reason\",\"title\":\"其他补充\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true},{\"type\":\"select\",\"field\":\"dept\",\"title\":\"所属部门\",\"info\":\"\",\"effect\":{\"fetch\":\"\"},\"options\":[{\"value\":\"技术部\",\"label\":\"技术部\"},{\"value\":\"产品部\",\"label\":\"产品部\"},{\"label\":\"市场部\",\"value\":\"市场部\"},{\"label\":\"人事部\",\"value\":\"人事部\"}],\"_fc_drag_tag\":\"select\",\"hidden\":false,\"display\":true},{\"type\":\"input\",\"field\":\"content\",\"title\":\"申请事由\",\"info\":\"\",\"props\":{\"type\":\"textarea\",\"rows\":4},\"_fc_drag_tag\":\"input\",\"hidden\":false,\"display\":true,\"validate\":[{\"trigger\":\"change\",\"mode\":\"required\",\"message\":\"必须输入\",\"required\":true,\"type\":\"string\"}]},{\"type\":\"switch\",\"field\":\"F9hn1n7twlkcfk\",\"title\":\"消息提示\",\"info\":\"\",\"_fc_drag_tag\":\"switch\",\"hidden\":false,\"display\":true},{\"type\":\"span\",\"title\":\"注意\",\"native\":false,\"children\":[\"有疑问找云尚小秘哦！\"],\"_fc_drag_tag\":\"span\",\"hidden\":false,\"display\":true}]',' ','feiyong','process/qingjia.zip','','申请费用',0,'2022-12-08 02:35:33','2023-12-16 14:18:07',0),(4,'旅游报销','https://gw.alicdn.com/tfs/TB1bHOWCSzqK1RjSZFjXXblCFXa-112-112.png','3','[{\"type\":\"datePicker\",\"field\":\"F5171ocmoaa97f\",\"title\":\"起止时间\",\"info\":\"\",\"$required\":\"必须输入起始时间\",\"props\":{\"type\":\"daterange\",\"align\":\"left\",\"editable\":false},\"_fc_drag_tag\":\"datePicker\",\"hidden\":false,\"display\":true},{\"type\":\"switch\",\"field\":\"F69w1ocmocqo5v\",\"title\":\"是否省内\",\"info\":\"\",\"$required\":true,\"_fc_drag_tag\":\"switch\",\"hidden\":false,\"display\":true},{\"type\":\"inputNumber\",\"field\":\"Fwju1ocwffsogj\",\"title\":\"几人团\",\"info\":\"\",\"$required\":true,\"_fc_drag_tag\":\"inputNumber\",\"hidden\":false,\"display\":true},{\"type\":\"rate\",\"field\":\"F8ya1ocmoag691\",\"title\":\"满意度\",\"info\":\"\",\"$required\":true,\"_fc_drag_tag\":\"rate\",\"hidden\":false,\"display\":true,\"value\":0}]','{\"form\":{\"labelPosition\":\"right\",\"size\":\"mini\",\"labelWidth\":\"125px\",\"hideRequiredAsterisk\":false,\"showMessage\":true,\"inlineMessage\":true},\"submitBtn\":true,\"resetBtn\":true}','lvyou','process\\lvyou.zip',NULL,'旅游报销',1,'2023-12-14 14:15:50','2023-12-17 01:15:36',0),(5,'驻场开发','https://gw.alicdn.com/tfs/TB1t695CFYqK1RjSZLeXXbXppXa-102-102.png','1','[{\"type\":\"el-transfer\",\"field\":\"Fdjq1ocqiamo2x\",\"title\":\"穿梭框\",\"info\":\"\",\"props\":{\"data\":[{\"key\":1,\"label\":\"备选项 1\",\"disabled\":false},{\"key\":2,\"label\":\"备选项 2\",\"disabled\":false},{\"key\":3,\"label\":\"备选项 3\",\"disabled\":false},{\"key\":4,\"label\":\"备选项 4\",\"disabled\":true},{\"key\":5,\"label\":\"备选项 5\",\"disabled\":false},{\"key\":6,\"label\":\"备选项 6\",\"disabled\":false},{\"key\":7,\"label\":\"备选项 7\",\"disabled\":false},{\"key\":8,\"label\":\"备选项 8\",\"disabled\":true},{\"key\":9,\"label\":\"备选项 9\",\"disabled\":false},{\"key\":10,\"label\":\"备选项 10\",\"disabled\":false},{\"key\":11,\"label\":\"备选项 11\",\"disabled\":false},{\"key\":12,\"label\":\"备选项 12\",\"disabled\":true},{\"key\":13,\"label\":\"备选项 13\",\"disabled\":false},{\"key\":14,\"label\":\"备选项 14\",\"disabled\":false},{\"key\":15,\"label\":\"备选项 15\",\"disabled\":false}]},\"_fc_drag_tag\":\"el-transfer\",\"hidden\":false,\"display\":true}]','{\"form\":{\"labelPosition\":\"right\",\"size\":\"mini\",\"labelWidth\":\"125px\",\"hideRequiredAsterisk\":false,\"showMessage\":true,\"inlineMessage\":false},\"submitBtn\":true,\"resetBtn\":false}','qingjia','process/qingjia.zip',NULL,'',0,'2023-12-15 13:25:16','2023-12-16 14:18:07',0);
/*!40000 ALTER TABLE `oa_process_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oa_process_type`
--

DROP TABLE IF EXISTS `oa_process_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oa_process_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '类型名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='审批类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oa_process_type`
--

LOCK TABLES `oa_process_type` WRITE;
/*!40000 ALTER TABLE `oa_process_type` DISABLE KEYS */;
INSERT INTO `oa_process_type` VALUES (1,'出勤','出勤','2022-12-06 01:35:12','2022-12-06 01:36:13',0),(2,'人事','人事','2022-12-06 01:35:19','2022-12-06 01:36:16',0),(3,'财务','财务','2022-12-06 01:35:29','2022-12-06 01:36:17',0),(4,'test','test2','2023-12-14 14:19:28','2023-12-14 14:19:35',1);
/*!40000 ALTER TABLE `oa_process_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(0:目录,1:菜单,2:按钮)',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort_value` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (2,0,'系统管理',0,'system','Layout',NULL,'el-icon-s-tools',1,1,'2021-05-31 10:05:37','2022-06-09 01:23:24',0),(3,2,'用户管理',1,'sysUser','system/sysUser/list','','el-icon-s-custom',1,1,'2021-05-31 10:05:37','2022-06-09 01:22:47',0),(4,2,'角色管理',1,'sysRole','system/sysRole/list','','el-icon-user-solid',2,1,'2021-05-31 10:05:37','2022-06-09 01:37:18',0),(5,2,'菜单管理',1,'sysMenu','system/sysMenu/list','','el-icon-s-unfold',3,1,'2021-05-31 10:05:37','2022-06-09 01:37:21',0),(6,3,'查看',2,NULL,NULL,'bnt.sysUser.list',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(7,3,'添加',2,NULL,NULL,'bnt.sysUser.add',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(8,3,'修改',2,NULL,NULL,'bnt.sysUser.update',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(9,3,'删除',2,NULL,NULL,'bnt.sysUser.remove',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(10,4,'查看',2,NULL,NULL,'bnt.sysRole.list',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(11,4,'添加',2,NULL,NULL,'bnt.sysRole.add',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(12,4,'修改',2,NULL,NULL,'bnt.sysRole.update',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(13,4,'删除',2,NULL,NULL,'bnt.sysRole.remove',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(14,5,'查看',2,NULL,NULL,'bnt.sysMenu.list',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(15,5,'添加',2,NULL,NULL,'bnt.sysMenu.add',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(16,5,'修改',2,NULL,NULL,'bnt.sysMenu.update',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(17,5,'删除',2,NULL,NULL,'bnt.sysMenu.remove',NULL,1,1,'2021-05-31 10:05:37','2022-06-09 01:22:38',0),(18,3,'分配角色',2,NULL,NULL,'bnt.sysUser.assignRole',NULL,1,1,'2022-05-23 09:14:32','2022-06-09 01:22:38',0),(19,4,'分配权限',2,'assignAuth','system/sysRole/assignAuth','bnt.sysRole.assignAuth',NULL,1,1,'2022-05-23 09:18:14','2022-06-09 01:22:38',0),(20,2,'部门管理',1,'sysDept','system/sysDept/list','','el-icon-s-operation',4,1,'2022-05-24 02:07:05','2023-12-14 07:05:56',1),(21,20,'查看',2,NULL,NULL,'bnt.sysDept.list',NULL,1,1,'2022-05-24 02:07:44','2023-12-14 07:07:48',1),(22,2,'岗位管理',1,'sysPost','system/sysPost/list','','el-icon-more-outline',5,1,'2022-05-24 02:25:30','2023-12-14 07:05:56',1),(23,22,'查看',2,NULL,NULL,'bnt.sysPost.list',NULL,1,1,'2022-05-24 02:25:45','2023-12-14 07:07:48',1),(24,20,'添加',2,NULL,NULL,'bnt.sysDept.add',NULL,1,1,'2022-05-25 07:31:27','2023-12-14 07:07:48',1),(25,20,'修改',2,NULL,NULL,'bnt.sysDept.update',NULL,1,1,'2022-05-25 07:31:41','2023-12-14 07:07:48',1),(26,20,'删除',2,NULL,NULL,'bnt.sysDept.remove',NULL,1,1,'2022-05-25 07:31:59','2023-12-14 07:07:48',1),(27,22,'添加',2,NULL,NULL,'bnt.sysPost.add',NULL,1,1,'2022-05-25 07:32:44','2023-12-14 07:07:48',1),(28,22,'修改',2,NULL,NULL,'bnt.sysPost.update',NULL,1,1,'2022-05-25 07:32:58','2023-12-14 07:07:48',1),(29,22,'删除',2,NULL,NULL,'bnt.sysPost.remove',NULL,1,1,'2022-05-25 07:33:11','2023-12-14 07:07:48',1),(30,34,'操作日志',1,'sysOperLog','system/sysOperLog/list','','el-icon-document-remove',7,1,'2022-05-26 08:09:59','2023-12-14 07:07:48',1),(31,30,'查看',2,NULL,NULL,'bnt.sysOperLog.list',NULL,1,1,'2022-05-26 08:10:17','2023-12-14 07:07:48',1),(32,34,'登录日志',1,'sysLoginLog','system/sysLoginLog/list','','el-icon-s-goods',8,1,'2022-05-26 08:36:13','2023-12-14 07:07:48',1),(33,32,'查看',2,NULL,NULL,'bnt.sysLoginLog.list',NULL,1,1,'2022-05-26 08:36:31','2023-12-14 07:07:48',1),(34,2,'日志管理',0,'log','ParentView','','el-icon-tickets',6,1,'2022-05-31 05:23:07','2023-12-14 07:05:56',1),(35,0,'审批设置',0,'processSet','Layout','','el-icon-setting',1,1,'2022-12-01 01:32:46','2022-12-01 01:32:46',0),(36,35,'审批模板',1,'processTemplate','processSet/processTemplate/list','','el-icon-s-help',2,1,'2022-12-01 01:37:08','2022-12-19 06:10:48',0),(37,36,'查看',2,'','','bnt.processTemplate.list','',1,1,'2022-12-01 01:37:49','2022-12-01 01:37:49',0),(38,36,'审批模板设置',2,'templateSet','processSet/processTemplate/templateSet','bnt.processTemplate.templateSet','',1,1,'2022-12-01 06:52:08','2023-12-14 08:49:39',0),(39,35,'审批类型',1,'processType','processSet/processType/list','','el-icon-s-unfold',1,1,'2022-12-02 06:46:18','2022-12-13 10:12:24',0),(40,39,'查看',2,'','','bnt.processType.list','',1,1,'2022-12-02 06:46:41','2022-12-02 06:46:41',0),(41,0,'审批管理',0,'processMgr','Layout','','el-icon-more-outline',1,1,'2022-12-02 06:48:11','2023-12-15 13:16:46',0),(42,41,'审批列表',1,'process','processMgr/process/list','','el-icon-document-remove',1,1,'2022-12-02 06:49:06','2023-12-15 13:16:46',0),(43,42,'查看',2,'','','bnt.process.list','',1,1,'2022-12-02 06:49:24','2023-12-15 13:16:46',0),(44,36,'在线流程设置',2,'onlineProcessSet','processSet/processTemplate/onlineProcessSet','bnt.processTemplate.onlineProcessSet','',1,1,'2022-12-08 02:13:15','2023-12-16 19:57:43',0),(45,39,'添加',2,'','','bnt.processType.add','',1,1,'2022-12-09 01:14:53','2023-12-14 07:12:32',0),(46,39,'修改',2,'','','bnt.processType.update','',1,1,'2022-12-09 01:15:10','2023-12-14 07:12:32',0),(47,39,'删除',2,'','','bnt.processType.remove','',1,1,'2022-12-09 01:15:25','2023-12-14 07:12:32',0),(48,36,'删除',2,'','','bnt.processTemplate.remove','',1,1,'2022-12-09 01:22:29','2023-12-14 07:12:32',0),(49,36,'发布',2,'','','bnt.processTemplate.publish','',1,1,'2022-12-09 01:24:47','2023-12-14 07:12:32',0),(50,0,'公众号菜单',0,'wechat','Layout','','el-icon-s-operation',1,1,'2022-12-13 01:06:58','2023-12-16 19:57:43',0),(51,50,'菜单列表',1,'menu','wechat/menu/list','','el-icon-s-help',1,1,'2022-12-13 01:07:52','2023-12-16 19:57:43',0),(52,51,'查看',2,'','','bnt.menu.list','',1,1,'2022-12-13 01:08:48','2023-12-16 19:57:43',0),(53,51,'添加',2,'','','bnt.menu.add','',1,1,'2022-12-13 08:29:25','2023-12-16 19:57:43',0),(54,51,'修改',2,'','','bnt.menu.update','',1,1,'2022-12-13 08:29:41','2023-12-16 19:57:43',0),(55,51,'删除',2,'','','bnt.menu.remove','',1,1,'2022-12-13 08:29:59','2023-12-16 19:57:43',0),(56,51,'删除微信菜单',2,'','','bnt.menu.removeMenu','',1,1,'2022-12-13 08:30:36','2023-12-16 19:57:43',0),(57,51,'同步微信菜单',2,'','','bnt.menu.syncMenu','',1,1,'2022-12-13 08:31:00','2023-12-16 19:57:43',0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (9,'系统管理员11','admin11','系统管理员11','2023-12-09 10:38:52','2023-12-12 01:26:24',0),(10,'系统管理员22','admin22','系统管理员22','2023-12-09 10:43:00','2023-12-12 01:26:24',0),(11,'系统管理员','admin','系统管理员','2023-12-09 10:44:01','2023-12-09 10:44:01',0),(12,'部门管理员1','dept1','部门管理员1','2023-12-09 10:44:57','2023-12-09 10:44:57',0),(13,'部门管理员2','dept2','部门管理员2','2023-12-09 11:00:55','2023-12-12 01:26:24',0),(14,'工作人员1','gzry','工作人员1','2023-12-10 07:25:02','2023-12-11 03:39:00',0),(15,'条线经理','line manager','条线经理','2023-12-10 07:26:14','2023-12-12 01:26:24',0),(16,'Test1','test1',NULL,'2023-12-11 05:40:15','2023-12-12 01:26:24',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `menu_id` bigint(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8 COMMENT='角色菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (33,9,2,'2023-12-12 08:32:50','2023-12-12 11:19:26',1),(34,9,3,'2023-12-12 08:32:50','2023-12-12 11:19:26',1),(35,9,58,'2023-12-12 11:19:26','2023-12-12 11:20:13',1),(36,9,59,'2023-12-12 11:19:26','2023-12-12 11:20:13',1),(37,9,60,'2023-12-12 11:19:26','2023-12-12 11:20:13',1),(38,9,58,'2023-12-12 11:20:13','2023-12-12 11:21:36',1),(39,9,59,'2023-12-12 11:20:13','2023-12-12 11:21:36',1),(40,9,60,'2023-12-12 11:20:13','2023-12-12 11:21:36',1),(41,9,50,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(42,9,51,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(43,9,56,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(44,9,58,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(45,9,59,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(46,9,60,'2023-12-12 11:21:36','2023-12-14 13:43:52',1),(47,12,2,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(48,12,3,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(49,12,6,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(50,12,7,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(51,12,8,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(52,12,9,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(53,12,18,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(54,12,4,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(55,12,10,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(56,12,11,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(57,12,12,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(58,12,13,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(59,12,19,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(60,12,5,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(61,12,14,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(62,12,15,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(63,12,16,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(64,12,17,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(65,12,20,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(66,12,21,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(67,12,24,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(68,12,25,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(69,12,26,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(70,12,22,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(71,12,23,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(72,12,27,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(73,12,28,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(74,12,29,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(75,12,34,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(76,12,30,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(77,12,31,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(78,12,32,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(79,12,33,'2023-12-12 11:21:48','2023-12-12 11:22:14',1),(80,12,2,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(81,12,3,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(82,12,6,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(83,12,7,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(84,12,8,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(85,12,9,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(86,12,18,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(87,12,4,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(88,12,10,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(89,12,11,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(90,12,12,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(91,12,13,'2023-12-12 11:22:14','2023-12-13 09:50:56',1),(92,12,19,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(93,12,5,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(94,12,14,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(95,12,15,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(96,12,16,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(97,12,17,'2023-12-12 11:22:14','2023-12-14 13:44:29',1),(98,9,2,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(99,9,3,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(100,9,6,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(101,9,7,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(102,9,8,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(103,9,9,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(104,9,18,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(105,9,4,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(106,9,10,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(107,9,11,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(108,9,12,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(109,9,13,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(110,9,19,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(111,9,5,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(112,9,14,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(113,9,15,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(114,9,16,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(115,9,17,'2023-12-14 13:43:52','2023-12-14 13:43:52',0),(116,10,2,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(117,10,3,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(118,10,6,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(119,10,7,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(120,10,8,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(121,10,9,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(122,10,18,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(123,10,4,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(124,10,10,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(125,10,11,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(126,10,12,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(127,10,13,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(128,10,19,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(129,10,5,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(130,10,14,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(131,10,15,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(132,10,16,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(133,10,17,'2023-12-14 13:44:04','2023-12-14 13:44:16',1),(134,11,2,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(135,11,3,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(136,11,6,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(137,11,7,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(138,11,8,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(139,11,9,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(140,11,18,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(141,11,4,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(142,11,10,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(143,11,11,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(144,11,12,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(145,11,13,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(146,11,19,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(147,11,5,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(148,11,14,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(149,11,15,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(150,11,16,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(151,11,17,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(152,11,35,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(153,11,36,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(154,11,37,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(155,11,38,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(156,11,48,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(157,11,49,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(158,11,39,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(159,11,40,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(160,11,45,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(161,11,46,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(162,11,47,'2023-12-14 13:44:11','2023-12-15 13:17:40',1),(163,10,35,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(164,10,36,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(165,10,37,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(166,10,38,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(167,10,48,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(168,10,49,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(169,10,39,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(170,10,40,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(171,10,45,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(172,10,46,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(173,10,47,'2023-12-14 13:44:16','2023-12-14 13:44:16',0),(174,14,35,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(175,14,36,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(176,14,37,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(177,14,38,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(178,14,48,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(179,14,49,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(180,14,39,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(181,14,40,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(182,14,45,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(183,14,46,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(184,14,47,'2023-12-14 13:44:45','2023-12-14 13:44:45',0),(185,11,2,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(186,11,3,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(187,11,6,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(188,11,7,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(189,11,8,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(190,11,9,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(191,11,18,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(192,11,4,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(193,11,10,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(194,11,11,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(195,11,12,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(196,11,13,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(197,11,19,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(198,11,5,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(199,11,14,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(200,11,15,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(201,11,16,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(202,11,17,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(203,11,35,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(204,11,36,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(205,11,37,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(206,11,38,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(207,11,48,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(208,11,49,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(209,11,39,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(210,11,40,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(211,11,45,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(212,11,46,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(213,11,47,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(214,11,41,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(215,11,42,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(216,11,43,'2023-12-15 13:17:40','2023-12-16 19:59:03',1),(217,11,2,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(218,11,3,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(219,11,6,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(220,11,7,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(221,11,8,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(222,11,9,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(223,11,18,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(224,11,4,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(225,11,10,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(226,11,11,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(227,11,12,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(228,11,13,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(229,11,19,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(230,11,5,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(231,11,14,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(232,11,15,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(233,11,16,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(234,11,17,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(235,11,35,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(236,11,36,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(237,11,37,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(238,11,38,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(239,11,48,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(240,11,49,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(241,11,39,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(242,11,40,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(243,11,45,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(244,11,46,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(245,11,47,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(246,11,41,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(247,11,42,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(248,11,43,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(249,11,50,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(250,11,51,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(251,11,52,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(252,11,53,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(253,11,54,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(254,11,55,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(255,11,56,'2023-12-16 19:59:03','2023-12-16 19:59:03',0),(256,11,57,'2023-12-16 19:59:03','2023-12-16 19:59:03',0);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `post_id` bigint(20) DEFAULT NULL COMMENT '岗位id',
  `open_id` varchar(255) DEFAULT NULL COMMENT '微信openId',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (13,'admin','21232f297a57a5a743894a0e4a801fc3','管理员','16514313137','https://crowdfunding-vectorx.oss-cn-shanghai.aliyuncs.com/20231210/1.png',NULL,NULL,'oWL__6qCykk30OZly25Mn8k46CEI','Deserunt repellendus molestiae mollitia ipsam expedita deleniti voluptates velit. Reiciendis amet sit in harum et non sint totam. Et possimus quibusdam corrupti aliquid rerum at sapiente aut. Totam dolorem consequatur harum voluptate repellat.',1,'2023-12-10 07:07:39','2023-12-17 14:45:34',0),(14,'zhangsan','098f6bcd4621d373cade4e832627b4f6','Brett Cremin','14831250811','',NULL,NULL,'oWL__6v1OfRBr4U2xid0CF8mUOes','',1,'2023-12-11 07:10:35','2023-12-17 14:43:07',0),(15,'lisi','ad0234829205b9033196ba818f7a872b','Joanna Marks','19747026472',NULL,NULL,NULL,'',NULL,1,'2023-12-11 09:21:15','2023-12-16 21:43:13',0),(17,'test4','86985e105f79b95d6bc918fb45ec7727','Laurie Weimann DVM','15897075470',NULL,NULL,NULL,NULL,NULL,1,'2023-12-11 09:29:14','2023-12-12 12:48:47',0),(18,'zhangsan01','e3d704f3542b44a621ebed70dc0efe13','Tracy Ondricka','13182156043',NULL,NULL,NULL,NULL,NULL,1,'2023-12-11 09:29:31','2023-12-14 02:34:43',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_admin_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,9,13,'2023-12-12 01:19:44','2023-12-12 02:40:23',1),(2,9,14,'2023-12-12 01:19:44','2023-12-13 09:36:12',1),(3,12,15,'2023-12-12 01:19:44','2023-12-13 09:49:13',1),(4,13,15,'2023-12-12 01:19:44','2023-12-13 09:49:13',1),(5,12,16,'2023-12-12 01:19:44','2023-12-14 13:19:40',0),(6,10,13,'2023-12-12 02:40:23','2023-12-12 02:41:13',1),(7,10,13,'2023-12-12 02:41:13','2023-12-12 02:41:18',1),(8,9,13,'2023-12-12 02:41:13','2023-12-12 02:41:18',1),(9,9,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(10,10,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(11,11,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(12,12,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(13,13,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(14,14,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(15,15,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(16,16,13,'2023-12-12 02:41:18','2023-12-12 02:41:21',1),(17,12,18,'2023-12-12 02:41:29','2023-12-14 13:45:50',1),(18,9,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(19,10,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(20,11,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(21,12,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(22,13,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(23,14,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(24,15,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(25,16,13,'2023-12-12 08:28:34','2023-12-12 08:28:38',1),(26,16,14,'2023-12-13 09:36:12','2023-12-13 09:47:33',1),(27,16,14,'2023-12-13 09:47:33','2023-12-13 09:48:28',1),(28,12,14,'2023-12-13 09:47:33','2023-12-13 09:48:28',1),(29,12,14,'2023-12-13 09:48:28','2023-12-14 13:45:14',1),(30,12,15,'2023-12-13 09:49:13','2023-12-14 13:45:18',1),(31,9,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(32,10,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(33,11,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(34,12,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(35,13,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(36,14,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(37,15,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(38,16,13,'2023-12-14 07:18:25','2023-12-14 13:45:28',1),(39,9,14,'2023-12-14 13:45:14','2023-12-14 13:45:14',0),(40,10,15,'2023-12-14 13:45:18','2023-12-14 13:45:18',0),(41,11,13,'2023-12-14 13:45:28','2023-12-14 13:45:28',0),(42,14,17,'2023-12-14 13:45:39','2023-12-14 13:45:39',0),(43,9,18,'2023-12-14 13:45:50','2023-12-14 13:45:50',0),(44,10,18,'2023-12-14 13:45:50','2023-12-14 13:45:50',0);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_menu`
--

DROP TABLE IF EXISTS `wechat_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `url` varchar(100) DEFAULT NULL COMMENT '网页 链接，用户点击菜单可打开链接',
  `meun_key` varchar(20) DEFAULT NULL COMMENT '菜单KEY值，用于消息接口推送',
  `sort` tinyint(3) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_menu`
--

LOCK TABLES `wechat_menu` WRITE;
/*!40000 ALTER TABLE `wechat_menu` DISABLE KEYS */;
INSERT INTO `wechat_menu` VALUES (2,0,'审批列表',NULL,NULL,NULL,1,'2022-12-13 01:23:30','2022-12-13 01:29:22',0),(3,0,'审批中心','view','/',NULL,2,'2022-12-13 01:23:44','2022-12-13 01:54:20',0),(4,0,'我的',NULL,NULL,NULL,3,'2022-12-13 01:23:52','2022-12-13 01:29:24',0),(5,2,'待处理','view','/list/0','',1,'2022-12-13 01:19:56','2022-12-13 01:24:10',0),(6,2,'已处理','view','/list/1','',2,'2022-12-13 01:27:00','2022-12-13 01:29:28',0),(7,2,'已发起','view','/list/1','',3,'2022-12-13 01:27:30','2022-12-13 01:29:30',0),(8,4,'基本信息','view','/user','',1,'2022-12-13 01:28:47','2022-12-13 01:28:47',0),(9,4,'关于我们','view','/about','',2,'2022-12-13 01:29:08','2022-12-13 01:29:32',0),(10,4,'测试','view','/test','',3,'2023-12-17 01:25:02','2023-12-17 01:32:27',0);
/*!40000 ALTER TABLE `wechat_menu` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-08 22:54:31
