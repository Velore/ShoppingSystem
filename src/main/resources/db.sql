# id类字段的存储类型为varchar(16)
# 名字类字段的存储类型为varchar(16):最多输入4位中文字符

create database shopping_system character set utf8;

create table `user`(
	user_id varchar(16) not null primary key comment '用户id',
	username varchar(16) not null default 'user' comment '用户名',
	password varchar(16) not null comment '密码'
)comment '用户';

create table product(
	product_id varchar(16) not null primary key comment '商品id',
	product_name varchar(16) not null default 'product' comment '商品名',
	description text null default null comment '商品描述'
)comment '商品';

create table market(
	market_id varchar(16) not null primary key comment '超市id',
	market_name varchar(16) not null default 'market' comment '超市名',
	user_id varchar(16) not null comment '超市负责人id',

	foreign key (user_id) references `user`(user_id)
)comment '超市';

create table market_store(
	# 无意义主键
	id int not null primary key auto_increment,
	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	store_num int not null comment '存储数量',

	foreign key (market_id) references market(market_id),
	foreign key (product_id) references product(product_id)
)comment '超市存货';

create table `order`(
	order_id varchar(16) not null primary key comment '订单id',
	user_id varchar(16) not null comment '订购者id',
	market_id varchar(16) not null comment '超市id',
	product_id varchar(16) not null comment '商品id',
	order_num int not null comment '订购数量',
	# 订单状态包括:0:等待处理,1:订购完成,2:订购失败,3:取消订单
	order_status int not null default 0 comment '订单状态',

	foreign key (user_id) references `user`(user_id),
	foreign key (market_id) references market(market_id),
	foreign key (product_id) references product(product_id)
)comment '订单';

insert into `user`(user_id, username, password) values
     ('u1','用户1','admin')
    ,('u2','用户2','admin')
    ,('u3','用户3','admin');

insert into product(product_id, product_name, description) values
     ('pa','产品a','产品a的描述')
    ,('pb','产品b','产品b的描述')
    ,('pc','产品c','产品c的描述');

insert into market(market_id, market_name, user_id) values
     ('ma','超市a','u1')
    ,('mb','超市b','u2')
    ,('mc','超市c','u2');