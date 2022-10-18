/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo_fc

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 05/10/2022 15:15:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for condition_type
-- ----------------------------
DROP TABLE IF EXISTS `condition_type`;
CREATE TABLE `condition_type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of condition_type
-- ----------------------------
INSERT INTO `condition_type` VALUES (1, 'A', 'Top', '/img/top-condition.PNG', '2022-10-05 10:16:15', '2022-10-05 10:16:15');
INSERT INTO `condition_type` VALUES (2, 'B', 'Good', '/img/good-condition.PNG', '2022-10-05 10:16:15', '2022-10-05 10:16:15');
INSERT INTO `condition_type` VALUES (3, 'C', 'Normal', '/img/normal-condition.PNG', '2022-10-05 10:16:15', '2022-10-05 10:16:15');
INSERT INTO `condition_type` VALUES (4, 'D', 'Poor', '/img/poor-condition.PNG', '2022-10-05 10:16:15', '2022-10-05 10:16:15');
INSERT INTO `condition_type` VALUES (5, 'E', 'Terrible', '/img/terrible-condition.PNG', '2022-10-05 10:16:15', '2022-10-05 10:16:15');

SET FOREIGN_KEY_CHECKS = 1;
