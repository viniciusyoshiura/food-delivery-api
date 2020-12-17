create table payment_method (
	id bigint not null auto_increment, 
	description varchar(60) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table groupinge (
	id bigint not null auto_increment, 
	name varchar(60) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table groupinge_permission (
	groupinge_id bigint not null, 
	permission_id bigint not null
) engine=InnoDB default charset=utf8;

create table permission (
	id bigint not null auto_increment, 
	description varchar(60) not null, 
	name varchar(100) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table product (
	id bigint not null auto_increment, 
	active tinyint(1) not null, 
	description text not null, 
	name varchar(80) not null, 
	price decimal(10,2) not null, 
	restaurant_id bigint not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (
	id bigint not null auto_increment, 
	address_complement varchar(60), 
	address_district varchar(60), 
	address_number varchar(20), 
	address_street varchar(100), 
	address_zip_code varchar(9), 
	date_register datetime not null, 
	date_update datetime not null, 
	name varchar(80) not null, 
	shipping_fee decimal(10,2) not null,
	address_city_id bigint, 
	kitchen_id bigint not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment_method (
	restaurant_id bigint not null, 
	payment_method_id bigint not null
) engine=InnoDB default charset=utf8;

create table user (
	id bigint not null auto_increment, 
	date_register datetime not null, 
	email varchar(255) not null, 
	name varchar(80) not null, 
	password varchar(255) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table user_groupinge (
	user_id bigint not null, 
	groupinge_id bigint not null
) engine=InnoDB default charset=utf8;


alter table groupinge_permission add constraint fk_groupinge_permission_permission 
foreign key (permission_id) references permission (id);

alter table groupinge_permission add constraint fk_groupinge_permission_groupinge
foreign key (groupinge_id) references groupinge (id);

alter table product add constraint fk_product_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_kitchen 
foreign key (kitchen_id) references kitchen (id);

alter table restaurant add constraint fk_restaurant_city 
foreign key (address_city_id) references city (id);

alter table restaurant_payment_method add constraint fk_restaurant_payment_method_payment_method 
foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method add constraint fk_restaurant_payment_method_restaurant 
foreign key (restaurant_id) references restaurant (id);

alter table user_groupinge add constraint fk_user_groupinge_groupinge
foreign key (groupinge_id) references groupinge (id);

alter table user_groupinge add constraint fk_user_groupinge_user 
foreign key (user_id) references user (id);
