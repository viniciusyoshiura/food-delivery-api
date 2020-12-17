set foreign_key_checks = 0;

delete from city;
delete from kitchen;
delete from state;
delete from payment_method;
delete from groupinge;
delete from groupinge_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from user;
delete from user_groupinge;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table groupinge auto_increment = 1;
alter table groupinge_permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;

insert into kitchen (name) values ('Thai');
insert into kitchen (name) values ('Indian');
insert into kitchen (id, name) values (3, 'Argentine');
insert into kitchen (id, name) values (4, 'Brazilian');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);
insert into city (id, name, state_id) values (6, 'Ribeirão Preto', 2);

insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, address_city_id, address_zip_code, address_street, address_number, address_district, address_complement) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, 6, '14025-450', 'Av. Sumaré', '84', 'Jardim Sumaré', 'Galeria do Ipê');
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active) values (2,  'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active) values (3, 'Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp, true);

insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true);

insert into payment_method (id, description) values (1, 'Credit card');
insert into payment_method (id, description) values (2, 'Debit card');
insert into payment_method (id, description) values (3, 'Cash');

insert into permission (id, name, description) values (1, 'SEARCH_KITCHENS', 'Allows you to search kitchens');
insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows you to edit kitchens');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into product (name, description, price, active, restaurant_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into product (name, description, price, active, restaurant_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into product (name, description, price, active, restaurant_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into groupinge (name) values ('Manager'), ('Salesman'), ('Secretary'), ('Register');