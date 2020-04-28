-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: aurora
-- ------------------------------------------------------
-- Server version	8.0.19

-- 创建数据库
CREATE DATABASE `aurora`;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` char(32) NOT NULL COMMENT '用户id',
  `name` varchar(45) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `mail` varchar(60) DEFAULT NULL COMMENT '用户邮箱',
  `sex` enum('男','女') DEFAULT '男' COMMENT '用户性别',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(255) DEFAULT NULL COMMENT '用户个性签名',
  `fettle` enum('0','1','2') DEFAULT '0' COMMENT '账号状态, 0 正常, 1 封禁 2 注销',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_mail` (`mail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

--
-- Table structure for table `user_collection`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_collection` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户收藏id',
  `user_id` char(32) NOT NULL COMMENT '用户id',
  `video_id` char(32) NOT NULL COMMENT '视频id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户收藏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collection`
--

--
-- Table structure for table `user_comment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_comment` (
  `id` char(32) NOT NULL COMMENT '用户评论id',
  `video_id` char(32) NOT NULL COMMENT '视频id',
  `send_id` char(32) NOT NULL COMMENT '发送者id',
  `answer_id` char(32) DEFAULT NULL COMMENT '要回复的评论id',
  `comment_content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `likes` int unsigned DEFAULT '0' COMMENT '点赞数',
  `dislikes` int unsigned DEFAULT '0' COMMENT '踩数',
  `ancestry_id` char(32) DEFAULT NULL COMMENT '祖父评论(一级评论)的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_comment`
--

--
-- Table structure for table `user_gives`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_gives` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户点赞id',
  `user_id` char(32) DEFAULT NULL COMMENT '用户id',
  `target_id` char(32) NOT NULL COMMENT '点赞项id',
  `type` int NOT NULL COMMENT '点赞类型(0: 视频,1: 评论)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `action_type` tinyint(1) NOT NULL COMMENT '点赞类型:[ 0: 点赞, 1: 踩]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='视频类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_gives`
--

--
-- Table structure for table `video`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `video` (
  `id` char(32) NOT NULL COMMENT '视频id',
  `author_id` char(32) NOT NULL COMMENT '作者id',
  `title` varchar(60) NOT NULL COMMENT '视频标题',
  `introduction` varchar(255) DEFAULT NULL COMMENT '视频介绍',
  `kind_id` int NOT NULL COMMENT '视频类型',
  `cover_url` varchar(255) NOT NULL COMMENT '视频封面地址',
  `video_url` varchar(255) NOT NULL COMMENT '视频地址',
  `fettle` enum('0','1','2') DEFAULT '0' COMMENT '视频状态, 0 正常, 1 已删除, 2 封禁',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `likes` int unsigned DEFAULT '0' COMMENT 'ç‚¹èµžæ•°',
  `dislikes` int unsigned DEFAULT '0' COMMENT '踩数',
  `collections` int unsigned DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

--
-- Table structure for table `video_kind`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `video_kind` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '视频类型id',
  `name` char(32) NOT NULL COMMENT '视频类型名',
  `info` varchar(32) DEFAULT NULL COMMENT '视频类型介绍',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_video_kind_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='视频类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_kind`
--

LOCK TABLES `video_kind` WRITE;
INSERT INTO `video_kind` VALUES (5,'生活','日常琐事题材','2020-04-22 13:33:49',NULL),(6,'音乐','MV题材','2020-04-22 13:34:43',NULL),(7,'摄影','旅行记事题材','2020-04-22 13:37:28',NULL),(8,'动漫','番剧漫减题材','2020-04-22 13:38:52',NULL);
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-23 15:32:40
