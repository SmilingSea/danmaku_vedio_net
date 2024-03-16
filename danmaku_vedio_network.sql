/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : danmaku_video_network

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 12/03/2024 19:21:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` bigint NOT NULL COMMENT '评论id',
  `father_id` bigint NOT NULL COMMENT '父id',
  `video_id` bigint NOT NULL COMMENT '子id',
  `content` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `user_id` bigint NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (1727332480144515074, 1727328822635921410, 1727328822635921410, 'nostrud', 1726923849846722561, '2023-11-22 22:25:30');
INSERT INTO `tb_comment` VALUES (1727332549979668482, 1727332480144515074, 1727328822635921410, 'hao', 1726923849846722561, '2023-11-22 22:25:47');
INSERT INTO `tb_comment` VALUES (1737804935007027201, 1, 1, 'officia Ut Excepteur', 1725839824566505474, '2023-12-21 19:59:18');

-- ----------------------------
-- Table structure for tb_socializing
-- ----------------------------
DROP TABLE IF EXISTS `tb_socializing`;
CREATE TABLE `tb_socializing`  (
  `id` bigint NOT NULL COMMENT '表id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `follow_id` bigint NOT NULL COMMENT '该用户关注人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_socializing
-- ----------------------------
INSERT INTO `tb_socializing` VALUES (1726935634674864129, 1725839824566505474, 1725839671734456321);
INSERT INTO `tb_socializing` VALUES (1726936775672356865, 1726923849846722561, 1725839824566505474);
INSERT INTO `tb_socializing` VALUES (1726936887391838209, 1725839824566505474, 1726923849846722561);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint NOT NULL COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号码',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号码',
  `profile` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `blocked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户是否被封禁',
  `identity` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户身份：0为普通用户，1为管理员',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1725839671734456321, 'smilingsea', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1725839824566505474, 'jhh', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/avatar/1737040484372742146.png');
INSERT INTO `tb_user` VALUES (1726923849846722561, 'jianghaohuan', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1729906415180603393, 'feige', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1736328554762874881, 'feigeshuai', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1737718996054233089, 'haohuan', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1761033552742367234, 'Smlingsea', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);
INSERT INTO `tb_user` VALUES (1767510974006398977, 'jack', 'e10adc3949ba59abbe56e057f20f883e', 'x.dmnjvgwav@qq.com', '13162626635', NULL, 0, '0', NULL);

-- ----------------------------
-- Table structure for tb_video
-- ----------------------------
DROP TABLE IF EXISTS `tb_video`;
CREATE TABLE `tb_video`  (
  `id` bigint NOT NULL COMMENT '视频id',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频标题',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频标题',
  `time` timestamp NOT NULL COMMENT '视频发布时间',
  `type` enum('动作','科幻','抽象','恐怖','喜剧','生活') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频分类',
  `usrid` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_video
-- ----------------------------
INSERT INTO `tb_video` VALUES (1, 'vedio1', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/55a01d9df426ce4a9d14fec0aeeb8970.mp4', '2023-11-22 22:10:54', '动作', 0);
INSERT INTO `tb_video` VALUES (1727325724119097346, '好看的视频', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727325719702495234.mp4', '2023-11-22 22:10:54', '动作', 0);
INSERT INTO `tb_video` VALUES (1727328822635921410, '好看的视频', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1727328748249939969.mp4', '2023-11-22 22:10:54', '动作', 0);
INSERT INTO `tb_video` VALUES (1732042010782748674, '好看的视频', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1732042008681402369.mp4', '2023-12-05 22:19:30', '喜剧', 1725839824566505474);
INSERT INTO `tb_video` VALUES (1736312070615523329, '好看的视频', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1736312038545874945.mp4', '2023-12-17 17:07:11', '喜剧', 1725839824566505474);
INSERT INTO `tb_video` VALUES (1736312077116694530, '好看的视频', 'https://danmaku-vedio-network-1322480945.cos.ap-nanjing.myqcloud.com/video/1736312075317338114.mp4', '2023-12-17 17:07:13', '喜剧', 1725839824566505474);

SET FOREIGN_KEY_CHECKS = 1;
