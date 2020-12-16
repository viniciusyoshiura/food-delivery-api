create table city (id bigint not null auto_increment, name varchar(255) not null, state_id bigint not null, primary key (id)) engine=InnoDB
create table grouping (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table grouping_permission (grouping_id bigint not null, permission_id bigint not null) engine=InnoDB
create table kitchen (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table payment_method (id bigint not null auto_increment, description varchar(255) not null, primary key (id)) engine=InnoDB
create table permission (id bigint not null auto_increment, description varchar(255) not null, name varchar(255) not null, primary key (id)) engine=InnoDB
create table product (id bigint not null auto_increment, active bit not null, description varchar(255) not null, name varchar(255) not null, price decimal(19,2) not null, restaurant_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant (id bigint not null auto_increment, address_complement varchar(255), address_district varchar(255), address_number varchar(255), address_street varchar(255), address_zip_code varchar(255), date_register datetime not null, date_update datetime not null, name varchar(255) not null, shipping_fee decimal(19,2) not null, address_city_id bigint, kitchen_id bigint not null, primary key (id)) engine=InnoDB
create table restaurant_payment_method (restaurant_id bigint not null, payment_method_id bigint not null) engine=InnoDB
create table state (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, date_register datetime not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB
create table user_grouping (user_id bigint not null, grouping_id bigint not null) engine=InnoDB
alter table city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state (id)
alter table grouping_permission add constraint FK6dydybgggi143y2a1aw6cr46e foreign key (permission_id) references permission (id)
alter table grouping_permission add constraint FKkqy9xae989rmuq3dbe4wp0t7y foreign key (grouping_id) references grouping (id)
alter table product add constraint FKp4b7e36gck7975p51raud8juk foreign key (restaurant_id) references restaurant (id)
alter table restaurant add constraint FK8pcwgn41lfg43d8u82ewn3cn foreign key (address_city_id) references city (id)
alter table restaurant add constraint FKrur1dqx79jim8s8dvdp16qc3d foreign key (kitchen_id) references kitchen (id)
alter table restaurant_payment_method add constraint FK5dxd5cjhjqf8eai6xugad3e1g foreign key (payment_method_id) references payment_method (id)
alter table restaurant_payment_method add constraint FKbjuwyavt07p2uihdqt6jtmkyj foreign key (restaurant_id) references restaurant (id)
alter table user_grouping add constraint FK7e3kf0xylp0d65b9tjbbiuele foreign key (grouping_id) references grouping (id)
alter table user_grouping add constraint FKew23mv5tylxlotw85svao3xu3 foreign key (user_id) references user (id)
insert into kitchen (name) values ('Thai')
insert into kitchen (name) values ('Indian')
insert into kitchen (id, name) values (3, 'Argentine')
insert into kitchen (id, name) values (4, 'Brazilian')
insert into state (id, name) values (1, 'Minas Gerais')
insert into state (id, name) values (2, 'São Paulo')
insert into state (id, name) values (3, 'Ceará')
insert into city (id, name, state_id) values (1, 'Uberlândia', 1)
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1)
insert into city (id, name, state_id) values (3, 'São Paulo', 2)
insert into city (id, name, state_id) values (4, 'Campinas', 2)
insert into city (id, name, state_id) values (5, 'Fortaleza', 3)
insert into city (id, name, state_id) values (6, 'Ribeirão Preto', 2)
insert into restaurant (name, shipping_fee, kitchen_id, date_register, date_update, address_city_id, address_zip_code, address_street, address_number, address_district, address_complement) values ('Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 6, '14025-450', 'Av. Sumaré', '84', 'Jardim Sumaré', 'Galeria do Ipê')
insert into restaurant (name, shipping_fee, kitchen_id, date_register, date_update) values ('Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp)
insert into restaurant (name, shipping_fee, kitchen_id, date_register, date_update) values ('Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp)
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp)
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp)
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp)
insert into payment_method (id, description) values (1, 'Credit card')
insert into payment_method (id, description) values (2, 'Debit card')
insert into payment_method (id, description) values (3, 'Cash')
insert into permission (id, name, description) values (1, 'SEARCH_KITCHENS', 'Allows you to search kitchens')
insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows you to edit kitchens')
insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3)
insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1)
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1)
insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2)
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3)
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3)
insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4)
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4)
insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5)
insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6)
