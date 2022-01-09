# id类字段的存储类型为varchar(16)
# 名字类字段的存储类型为varchar(16):最多输入4位中文字符

create database shopping_system_5320 character set utf8;

drop database shopping_system_5320;

create table `user_5320`(
	user_id varchar(16) not null primary key comment '用户id',
	username varchar(16) not null default 'user' comment '用户名',
	password varchar(16) not null comment '密码'
)comment '用户';

create table product_5320(
	product_id varchar(16) not null primary key comment '商品id',
	product_name varchar(16) not null default 'product' comment '商品名',
	description text null default null comment '商品描述'
)comment '商品';

create table market_5320(
	market_id varchar(16) not null primary key comment '超市id',
	market_name varchar(16) not null default 'market' comment '超市名',
	user_id varchar(16) null default null comment '超市负责人id',

	foreign key (user_id) references `user_5320`(user_id)
)comment '超市';

create table store_5320(

	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	store_num int not null comment '存储数量',

    primary key (market_id, product_id),
	foreign key (market_id) references market_5320(market_id),
	foreign key (product_id) references product_5320(product_id)
)comment '超市存货';

create table `order_5320`(
	order_id varchar(16) not null primary key comment '订单id',
	user_id varchar(16) not null comment '订购者id',
	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	order_num int not null comment '订购数量',
	# 订单状态包括:0:等待处理,1:订购完成,2:订购失败,3:取消订单
	order_status int not null default 0 comment '订单状态',

	foreign key (user_id) references `user_5320`(user_id),
	foreign key (market_id) references market_5320(market_id),
	foreign key (product_id) references product_5320(product_id)
)comment '订单';

insert into `user_5320`(user_id, username, password) values
     ('u1','用户1','p1')
    ,('u2','用户2','p2')
    ,('u3','用户3','p3');

insert into product_5320(product_id, product_name, description) values
     ('p001','prod_a','产品a的描述')
    ,('p002','prod_b','产品b的描述')
    ,('p003','prod_c','产品c的描述')
    ,('p004','prod_d','产品d的描述');

insert into market_5320(market_id, market_name, user_id) values
     ('m001','超市a','u1')
    ,('m002','超市b','u2')
    ,('m003','超市c','u2')
    ,('m004','超市d','u3')
    ,('m005','超市e','u1');