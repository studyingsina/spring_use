CREATE TABLE `mq` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `send-appkey` varchar(256) NOT NULL COMMENT '发送端appkey',
  `name` varchar(256) NOT NULL COMMENT '消息名称',
  `content` varchar(256) NOT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

CREATE TABLE `send_receive_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `send-appkey` varchar(256) NOT NULL COMMENT '发送端appkey',
  `receive-appkey` varchar(256) NOT NULL COMMENT '接收端appkey',
  `name` varchar(256) NOT NULL COMMENT '消息名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发送方和接收方关联表';

CREATE TABLE `send_receive_mq_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `send-appkey` varchar(256) NOT NULL COMMENT '发送端appkey',
  `receive-appkey` varchar(256) NOT NULL COMMENT '接收端appkey',
  `name` varchar(256) NOT NULL COMMENT '消息名称',
  `mq_id` int(11) NOT NULL COMMENT '消息id',
  `status` smallint(4) NOT NULL COMMENT '1-接收成功,2-发送成功',
  PRIMARY KEY (`id`),
  KEY `idx_mq_id` (`mq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发送方和接收方消息关联表';