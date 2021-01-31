create table price_tag_elec_syn_bak
(
   id                   bigint not null comment 'ID',
   syn_id               varchar(32) not null comment '同步ID',
   create_time          varchar(19) not null comment '创建时间',
   syn_status           smallint not null default 0 comment '同步状态(0新生成1已发成功2已发失败)',
   fail_times           smallint not null default 0 comment '失败次数',
   fail_msg             varchar(255) comment '失败信息',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   std_price_change_time varchar(19) not null comment '标准价变价时间',
   std_price            int not null default 0 comment '标牌价',
   std_price_seq        varchar(64) not null comment '标牌价流水号',
   shop_price_change_time varchar(19) not null comment '门店价变价时间',
   shop_price           int not null default 0 comment '门店价',
   shop_price_seq       varchar(64) not null comment '门店价流水号',
   shop_price_type      varchar(4) not null comment '门店价格类型',
   promo_price_begin_time varchar(19) comment '活动价开始时间',
   promo_price_end_time varchar(19) comment '活动价结束时间',
   price_controller     varchar(16) comment '物价员',
   sku_info_change_time varchar(19) not null comment '商品信息更新时间',
   sku_name             varchar(255) not null comment '商品名称',
   sku_sales_point      varchar(255) comment '商品卖点',
   sku_spec             varchar(100) comment '商品规格',
   sku_grade            varchar(20) comment '商品等级',
   sku_size             varchar(120) comment '商品尺寸',
   sku_made_in          varchar(20) comment '商品产地',
   sku_unit             varchar(10) comment '计量单位',
   qr_code_url          varchar(255) comment '二维码URL',
   paidup_vip_change_time varchar(19) comment '付费会员更新时间',
   paidup_vip_label     varchar(20) comment '付费会员标签',
   paidup_vip_disc      varchar(10) comment '付费会员折扣',
   promo_change_time    varchar(19) comment '促销更新时间',
   promo_info           varchar(255) comment '促销信息',
   warranty_change_time varchar(19) comment '延保更新时间',
   warranty_info        varchar(255) comment '延保信息',
   instalment_info      varchar(255) comment '分期付款信息',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '电子价签同步备份';

create table price_tag_elec_syn
(
   id                   bigint not null auto_increment comment 'ID',
   syn_id               varchar(32) not null comment '同步ID',
   create_time          varchar(19) not null comment '创建时间',
   syn_status           smallint not null default 0 comment '同步状态(0新生成1已发成功2已发失败)',
   fail_times           smallint not null default 0 comment '失败次数',
   fail_msg             varchar(255) comment '失败信息',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   std_price_change_time varchar(19) not null comment '标准价变价时间',
   std_price            int not null default 0 comment '标牌价',
   std_price_seq        varchar(64) not null comment '标牌价流水号',
   shop_price_change_time varchar(19) not null comment '门店价变价时间',
   shop_price           int not null default 0 comment '门店价',
   shop_price_seq       varchar(64) not null comment '门店价流水号',
   shop_price_type      varchar(4) not null comment '门店价格类型',
   promo_price_begin_time varchar(19) comment '活动价开始时间',
   promo_price_end_time varchar(19) comment '活动价结束时间',
   price_controller     varchar(16) comment '物价员',
   sku_info_change_time varchar(19) not null comment '商品信息更新时间',
   sku_name             varchar(255) not null comment '商品名称',
   sku_sales_point      varchar(255) comment '商品卖点',
   sku_spec             varchar(100) comment '商品规格',
   sku_grade            varchar(20) comment '商品等级',
   sku_size             varchar(120) comment '商品尺寸',
   sku_made_in          varchar(20) comment '商品产地',
   sku_unit             varchar(10) comment '计量单位',
   qr_code_url          varchar(255) comment '二维码URL',
   paidup_vip_change_time varchar(19) comment '付费会员更新时间',
   paidup_vip_label     varchar(20) comment '付费会员标签',
   paidup_vip_disc      varchar(10) comment '付费会员折扣',
   promo_change_time    varchar(19) comment '促销更新时间',
   promo_info           varchar(255) comment '促销信息',
   warranty_change_time varchar(19) comment '延保更新时间',
   warranty_info        varchar(255) comment '延保信息',
   instalment_info      varchar(255) comment '分期付款信息',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '电子价签同步';

create table price_tag_elec
(
   id                   bigint not null auto_increment comment 'ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   std_price_change_time varchar(19) not null comment '标准价变价时间',
   std_price            int not null default 0 comment '标牌价',
   std_price_seq        varchar(64) not null comment '标牌价流水号',
   shop_price_change_time varchar(19) not null comment '门店价变价时间',
   shop_price           int not null default 0 comment '门店价',
   shop_price_seq       varchar(64) not null comment '门店价流水号',
   shop_price_type      varchar(4) not null comment '门店价格类型',
   promo_price_begin_time varchar(19) comment '活动价开始时间',
   promo_price_end_time varchar(19) comment '活动价结束时间',
   price_controller     varchar(16) comment '物价员',
   sku_info_change_time varchar(19) not null comment '商品信息更新时间',
   sku_name             varchar(255) not null comment '商品名称',
   sku_sales_point      varchar(255) comment '商品卖点',
   sku_spec             varchar(100) comment '商品规格',
   sku_grade            varchar(20) comment '商品等级',
   sku_size             varchar(120) comment '商品尺寸',
   sku_made_in          varchar(20) comment '商品产地',
   sku_unit             varchar(10) comment '计量单位',
   qr_code_url          varchar(255) comment '二维码URL',
   paidup_vip_change_time varchar(19) comment '付费会员更新时间',
   paidup_vip_label     varchar(20) comment '付费会员标签',
   paidup_vip_disc      varchar(10) comment '付费会员折扣',
   promo_change_time    varchar(19) comment '促销更新时间',
   promo_info           varchar(255) comment '促销信息',
   warranty_change_time varchar(19) comment '延保更新时间',
   warranty_info        varchar(255) comment '延保信息',
   instalment_info      varchar(255) comment '分期付款信息',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '电子价签内容';

create table price_shop_change_to_notice
(
   id                   bigint not null comment 'ID',
   sale_price_seq       varchar(64) not null comment '售价流水号',
   sales_shop           varchar(4) not null comment '销售门店',
   change_time          varchar(19) not null comment '变价时间',
   sales_org            varchar(4) comment '销售组织',
   sku_id               int not null comment '商品SKUID',
   vendor_code          varchar(10) comment '供货商代码',
   biz_type             varchar(2) comment '业务机型',
   price_type           varchar(4) comment '价格类型',
   sales_price          int not null default 0 comment '销售单价',
   price_quantity       int comment '价格商品数',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '门店变价待通知';

create table price_change_subscribed_staff
(
   id                   bigint not null auto_increment comment 'ID',
   staff_id             varchar(10) not null comment '员工ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   subscribe_time       varchar(19) not null comment '订阅时间',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '变价通知员工订阅';

create table price_change_subscribed_staff_log
(
   log_id               bigint not null auto_increment comment '日志ID',
   staff_id             varchar(10) not null comment '员工ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   is_cancel            smallint not null default 0 comment '是取消',
   log_time             varchar(19) not null comment '日志时间',
   syn_id               varchar(32) not null comment '同步ID',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (log_id)
) comment '变价通知员工订阅日志';

create table price_shop_sku_day
(
   id                   bigint not null auto_increment comment 'ID',
   change_date          varchar(10) not null comment '变价日期',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_price          int not null default 0 comment '销售单价',
   sales_org            varchar(4) comment '销售组织',
   print_status         smallint not null default 0 comment '打印状态(0变价未打1变价已打2未变价打印)',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '门店商品每日变价';

create table promo_change_items
(
   id                   bigint not null comment 'ID',
   label_type           varchar(8) not null comment '标签类型',
   label_desc           varchar(255) comment '标签描述',
   begin_time           varchar(19) comment '开始时间',
   end_time             varchar(19) comment '结束时间',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id, label_type)
) comment '促销变化记录明细';

create table promo_change
(
   id                   bigint not null auto_increment comment 'ID',
   trans_id             varchar(32) not null comment '事务ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   sales_org            varchar(4) comment '销售组织',
   change_time          varchar(19) not null comment '变价时间',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '促销变化记录';

create table shop_sku_osop
(
   id                   bigint not null auto_increment comment 'ID',
   sales_shop           varchar(4) not null comment '销售门店',
   sku_id               int not null comment '商品SKUID',
   shop_type            varchar(10) comment '店铺类型',
   status               smallint not null default 1 comment '状态(0下架1上架)',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '一店一页上架商品';

create table price_std_change
(
   id                   bigint not null auto_increment comment 'ID',
   sale_price_seq       varchar(64) not null comment '售价流水号',
   sales_org            varchar(4) comment '销售组织',
   change_time          varchar(19) not null comment '变价时间',
   sku_id               int not null comment '商品SKUID',
   vendor_code          varchar(10) comment '供货商代码',
   biz_type             varchar(2) comment '业务机型',
   sales_price          int not null default 0 comment '销售单价',
   change_status        smallint not null default 0 comment '变价状态(0未变1变了)',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment '标牌变价记录';

create table price_shop_change
(
   id                   bigint not null auto_increment comment 'ID',
   sale_price_seq       varchar(64) not null comment '售价流水号',
   sales_shop           varchar(4) not null comment '销售门店',
   change_time          varchar(19) not null comment '变价时间',
   sales_org            varchar(4) comment '销售组织',
   sku_id               int not null comment '商品SKUID',
   vendor_code          varchar(10) comment '供货商代码',
   biz_type             varchar(2) comment '业务机型',
   price_type           varchar(4) comment '价格类型',
   sales_price          int not null default 0 comment '销售单价',
   price_quantity       int comment '价格商品数',
   change_status        smallint not null default 0 comment '变价状态(0未变1变了2已通知)',
   notice_time          varchar(19) not null comment '通知时间',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
) comment 'DPC门店变价记录';

create table price_shop_change_to_notice
(
   id                   bigint not null comment 'ID',
   sale_price_seq       varchar(64) not null comment '售价流水号',
   sales_shop           varchar(4) not null comment '销售门店',
   change_time          varchar(19) not null comment '变价时间',
   sales_org            varchar(4) comment '销售组织',
   sku_id               int not null comment '商品SKUID',
   vendor_code          varchar(10) comment '供货商代码',
   biz_type             varchar(2) comment '业务机型',
   price_type           varchar(4) comment '价格类型',
   sales_price          int not null default 0 comment '销售单价',
   price_quantity       int comment '价格商品数',
   last_update_time     datetime(3) not null comment '最后更新时间',
   primary key (id)
);