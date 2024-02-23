/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.32 : Database - danmaku_video_network
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`danmaku_video_network` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `danmaku_video_network`;

/*Table structure for table `tb_comment` */

DROP TABLE IF EXISTS `tb_comment`;

CREATE TABLE `tb_comment` (
  `id` bigint NOT NULL COMMENT '评论id',
  `father_id` bigint NOT NULL COMMENT '父id',
  `video_id` bigint NOT NULL COMMENT '子id',
  `content` varchar(45) NOT NULL,
  `user_id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `tb_comment` */

insert  into `tb_comment`(`id`,`father_id`,`video_id`,`content`,`user_id`,`create_time`) values (1727332480144515074,1727328822635921410,1727328822635921410,'nostrud',1726923849846722561,'2023-11-22 22:25:30'),(1727332549979668482,1727332480144515074,1727328822635921410,'hao',1726923849846722561,'2023-11-22 22:25:47'),(1737804935007027201,1,1,'officia Ut Excepteur',1725839824566505474,'2023-12-21 19:59:18');

/*Table structure for table `tb_socializing` */

DROP TABLE IF EXISTS `tb_socializing`;

CREATE TABLE `tb_socializing` (
  `id` bigint NOT NULL COMMENT '表id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `follow_id` bigint NOT NULL COMMENT '该用户关注人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `tb_socializing` */

insert  into `tb_socializing`(`id`,`user_id`,`follow_id`) values (1726935634674864129,1725839824566505474,1725839671734456321),(1726936775672356865,1726923849846722561,1725839824566505474),(1726936887391838209,1725839824566505474,1726923849846722561);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` bigint NOT NULL COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '手机号码',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号码',
  `profile` varchar(255) DEFAULT NULL COMMENT '头像url',
  `blocked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户是否被封禁',
  `identity` varchar(255) NOT NULL DEFAULT '0' COMMENT '用户身份：0为普通用户，1为管理员',
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`name`,`password`,`email`,`phone`,`profile`,`blocked`,`identity`,`avatar`) values (1725839671734456321,'smilingsea','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL),(1725839824566505474,'jhh','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/avatar/1737040484372742146.png'),(1726923849846722561,'jianghaohuan','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL),(1729906415180603393,'feige','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL),(1736328554762874881,'feigeshuai','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL),(1737718996054233089,'haohuan','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL),(1761033552742367234,'Smlingsea','e10adc3949ba59abbe56e057f20f883e','x.dmnjvgwav@qq.com','13162626635',NULL,0,'0',NULL);

/*Table structure for table `tb_video` */

DROP TABLE IF EXISTS `tb_video`;

CREATE TABLE `tb_video` (
  `id` bigint NOT NULL COMMENT '视频id',
  `title` varchar(255) NOT NULL COMMENT '视频标题',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频标题',
  `time` timestamp NOT NULL COMMENT '视频发布时间',
  `type` enum('动作','科幻','抽象','恐怖','喜剧','生活') NOT NULL COMMENT '视频分类',
  `usrid` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `tb_video` */

insert  into `tb_video`(`id`,`title`,`url`,`time`,`type`,`usrid`) values (1,'vedio1','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/55a01d9df426ce4a9d14fec0aeeb8970.mp4','2023-11-22 22:10:54','动作',0),(1727325724119097346,'好看的视频','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727325719702495234.mp4','2023-11-22 22:10:54','动作',0),(1727328822635921410,'好看的视频','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727328748249939969.mp4','2023-11-22 22:10:54','动作',0),(1732042010782748674,'好看的视频','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1732042008681402369.mp4','2023-12-05 22:19:30','喜剧',1725839824566505474),(1736312070615523329,'好看的视频','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1736312038545874945.mp4','2023-12-17 17:07:11','喜剧',1725839824566505474),(1736312077116694530,'好看的视频','https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1736312075317338114.mp4','2023-12-17 17:07:13','喜剧',1725839824566505474);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
