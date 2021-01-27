create table price_tag_elec_bind_log
(
   log_id               bigint not null auto_increment comment '日志ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   is_cancel            smallint not null default 0 comment '是取消',
   log_time             varchar(19) not null comment '日志时间',
   trans_id             varchar(32) not null comment '事务ID',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (log_id)
);