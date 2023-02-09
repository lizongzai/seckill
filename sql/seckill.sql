DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT(20) NOT NULL COMMENT '用户ID, 手机号码',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
    `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `salt` varchar(10) DEFAULT NULL,
    `head` varchar(128) DEFAULT NULL COMMENT '头像',
    `register_date` datetime DEFAULT NULL COMMENT '注册时间',
    `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
    `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
     PRIMARY KEY (`id`)
)