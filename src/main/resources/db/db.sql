# id类字段的存储类型为varchar(16)
# 名字类字段的存储类型为varchar(16)

create database shopping_system_5320 character set utf8;

drop database shopping_system_5320;

# 创建用户表
create table `user_5320`(
	user_id varchar(16) not null primary key comment '用户id',
	username varchar(16) not null default 'user' comment '用户名',
	password varchar(16) not null comment '密码'
)comment '用户';

# 创建商品表
create table product_5320(
	product_id varchar(16) not null primary key comment '商品id',
	product_name varchar(16) not null default 'product' comment '商品名',
	description text null default null comment '商品描述'
)comment '商品';

# 创建超市表
create table market_5320(
	market_id varchar(16) not null primary key comment '超市id',
	market_name varchar(16) not null default 'market' comment '超市名',
	user_id varchar(16) null default null comment '超市负责人id',

	foreign key (user_id) references `user_5320`(user_id)
)comment '超市';

# 创建超市库存表
create table store_5320(

	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	store_num int not null comment '存储数量',

    primary key (market_id, product_id),
	foreign key (market_id) references market_5320(market_id),
	foreign key (product_id) references product_5320(product_id)
)comment '超市库存';

# 创建订单表
create table `order_5320`(
	order_id varchar(16) not null primary key comment '订单id',
	user_id varchar(16) not null comment '订购者id',
	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	order_num int not null comment '订购数量',
	# 订单状态包括:0:等待处理,1:订购完成,2:订购失败,3:取消订单
	order_status int not null default 0 comment '订单状态',

	foreign key (user_id) references `user_5320`(user_id),
	foreign key (market_id) references market_5320(market_id)
)comment '订单';

insert into `user_5320`(user_id, username, password) values
     ('u001', '小明', 'pwd')
    ,('u002', '小红', 'pwd')
    ,('u003', '小王', 'pwd')
    ,('u004', '小李', 'pwd')
    # root 用户
    ,('root', 'admin', 'root');

insert into product_5320(product_id, product_name, description) values
     ('p001', '数据库原理', '描述')
    ,('p002', 'mysql数据库', '描述')
    ,('p003', 'redis数据库', '描述')
    ,('p004', '计算机网络', '描述')
    ,('p005', 'openGauss数据库', '描述')
    ,('p006', '计算机系统组成', '描述');

insert into market_5320(market_id, market_name, user_id) values
     ('m001', '超市u1', 'u001')
    ,('m002', '超市u2_1', 'u002')
    ,('m003', '超市u2_2', 'u002')
    ,('m004', '超市u3', 'u003')
    ,('m005', '超市u1_2', 'u001');

insert into store_5320(market_id, product_id, store_num) values
     ('m001', 'p001', 31),('m001', 'p003', 52),('m001', 'p005', 47)
    ,('m002', 'p001', 43),('m002', 'p002', 76),('m002', 'p005', 25)
    ,('m003', 'p002', 24),('m003', 'p004', 62),('m003', 'p006', 35)
    ,('m004', 'p003', 17);

insert into order_5320(order_id, user_id, market_id, product_id, order_num, order_status) values
    ('1', 'u001', 'm001', 'p001', 6,0),('3', 'u004', 'm001', 'p003', 23,0)
    ,('4', 'u002', 'm001', 'p002', 32,1),('11', 'u003', 'm001', 'p005', 25,2)
    ,('2', 'u003', 'm002', 'p001', 18,0),('6', 'u001', 'm002', 'p002', 56,1)
    ,('9', 'u004', 'm002', 'p003', 4,2),('15', 'u003', 'm003', 'p004', 37,3)
    ,('5', 'u002', 'm003', 'p003', 37,1),('12', 'u003', 'm003', 'p005', 27,1)
    ,('14', 'u001', 'm003', 'p001', 63,2),('16', 'u001', 'm004', 'p006', 74,2)
    ,('7', 'u003', 'm004', 'p002', 21,0),('8', 'u002', 'm003', 'p001', 9,0)
    ,('10', 'u004', 'm005', 'p004', 59,1),('13', 'u001', 'm005', 'p002', 55,3);