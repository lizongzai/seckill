DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT(20) NOT NULL COMMENT '用户ID, 手机号码',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
    `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `salt` varchar(10) DEFAULT NULL COMMENT '固定盐',
    `head` varchar(128) DEFAULT NULL COMMENT '头像',
    `register_date` datetime DEFAULT NULL COMMENT '注册时间',
    `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
    `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
     PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
    `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
    `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
    `goods_detail` longtext COMMENT '商品介绍详情',
    `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
    `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
    `create_date` datetime DEFAULT NULL COMMENT '商品入库时间',
    `update_date` datetime DEFAULT NULL COMMENT '商品补充时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
    `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
    `addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址id',
    `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
    `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
    `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
    `order_channel` int(2) DEFAULT '0' COMMENT '支付通道：1 PC、2 Android、3 ios',
    `status` int(2) DEFAULT NULL COMMENT '订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，‘5 已完成',
    `create_date` datetime DEFAULT NULL COMMENT '生成订单时间',
    `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
    `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
    `seckill_price` decimal(10,2) DEFAULT '0.0' NULL COMMENT '秒杀价',
    `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
    `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
    `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
    `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
    `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
     PRIMARY KEY (`id`),
     UNIQUE KEY `u_userid_goodsid` (`user_id`,`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- INSERT INTO `t_user` VALUES (18112345678,'admin','b7797cce01b4b131b433b6acf4add449','1a2b3c4d',null,'2023-02-10 10:29:40','2023-02-10 10:29:40',0);
--
-- INSERT INTO `t_goods` VALUES
-- (1,'iPhone 14 Pro',' 当天发/12分期/送大礼 Apple/苹果 iPhone 14 Pro 移动联通4G手机中移动','/img/iphone.jpg',' 当天发/12分期/送大礼 Apple/苹果 iPhone 14 Pro 移动联通4G手机中移动',8699.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20')
-- ,(2,'iPhone X',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动','/img/iphone.jpg',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动',7268.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20')
-- ,(3,'xiaomi 8',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se','/img/xiaomi.jpg',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se',2799.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20')
-- ,(4,'荣耀 10',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy','/img/rongyao.jpg',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy',2699.00,1000,'2018-07-12 19:06:20','2018-07-12 22:32:20')
-- ,(5,'oppo find x',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x','/img/oppo.jpg',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x',4999.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20');
--
--
-- INSERT INTO `t_seckill_goods` VALUES
-- (1,2,7699.00,94,'2023-02-14 19:06:20','2023-08-15 19:06:20')
-- ,(2,2,6888.00,94,'2023-02-12 19:06:20','2023-08-15 19:06:20')
-- ,(3,3,2699.00,95,'2023-02-17 22:32:20','2023-08-15 19:06:20')
-- ,(4,4,2599.00,93,'2023-02-23 00:59:20','2023-08-15 19:06:20')
-- ,(5,5,4999.00,97,'2023-02-17 09:06:20','2023-08-15 19:06:20');



INSERT INTO `t_user` VALUES (18112345678,'admin','b7797cce01b4b131b433b6acf4add449','1a2b3c4d',null,null,null,0);
INSERT INTO `t_goods` VALUES (1,' iPhone X',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动','/img/iphone.jpg',' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动',7268.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20'),(2,'xiaomi 8',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se','/img/xiaomi.jpg',' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se',2799.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20'),(3,'荣耀 10',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy','/img/rongyao.jpg',' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy',2699.00,1000,'2018-07-12 19:06:20','2018-07-12 22:32:20'),(4,'oppo find x',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x','/img/oppo.jpg',' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x',4999.00,1000,'2018-07-12 19:06:20','2018-07-12 19:06:20');


INSERT INTO `t_seckill_goods` VALUES (1,1,6888.00,94,'2018-07-12 19:06:20','2018-08-15 19:06:20'),(2,2,2699.00,95,'2018-07-17 22:32:20','2018-08-15 19:06:20'),(3,3,2599.00,93,'2018-07-14 00:59:20','2018-08-15 19:06:20'),(4,4,4999.00,97,'2018-07-17 09:06:20','2018-08-15 19:06:20');
