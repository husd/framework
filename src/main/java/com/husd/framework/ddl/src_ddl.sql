CREATE TEMPORARY TABLE IF NOT EXISTS `customer` (
`id` bigint auto_increment COMMENT 'ID',
`customer_id` bigint NOT NULL COMMENT '顾客ID',
customer_name varchar NOT NULL COMMENT '顾客姓名',
phone_num bigint(20) NOT NULL COMMENT '电话',
city_name varchar(10) NOT NULL COMMENT '所属城市',
sex int NOT NULL COMMENT '性别',
id_number varchar NOT NULL COMMENT '身份证号码',
home_address varchar NOT NULL COMMENT '家庭住址',
office_address varchar NOT NULL COMMENT '办公地址',
age int NOT NULL COMMENT '年龄',
login_time timestamp NOT NULL COMMENT '登录时间',
PRIMARY KEY (`id`),
index idx_customer_id (customer_id)
)
DISTRIBUTED BY HASH(customer_id)
PARTITION BY VALUE(DATE_FORMAT(login_time, '%Y%m%d')) LIFECYCLE 30
COMMENT '客户信息表';