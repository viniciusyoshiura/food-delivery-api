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
delete from restaurant_responsible_user;
delete from purchase_order;
delete from item_purchase_order;

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
alter table purchase_order auto_increment = 1;
alter table item_purchase_order auto_increment = 1;

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

insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open, address_city_id, address_zip_code, address_street, address_number, address_district, address_complement) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 6, '14025-450', 'Av. Sumaré', '84', 'Jardim Sumaré', 'Galeria do Ipê');
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open) values (2,  'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open) values (3, 'Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp, true, true);

insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurant (id, name, shipping_fee, kitchen_id, date_register, date_update, active, open) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into payment_method (id, description) values (1, 'Credit card');
insert into payment_method (id, description) values (2, 'Debit card');
insert into payment_method (id, description) values (3, 'Cash');

insert into permission (id, name, description) values (1, 'SEARCH_KITCHENS', 'Allows you to search kitchens');
insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows you to edit kitchens');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Pork with sweet and sour sauce', 'Delicious pork in special sauce', 78.90, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Thai shrimp', '16 large prawns in hot sauce', 110, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Spicy salad with grilled meat', 'Leaf salad with fine cuts of grilled beef and our special red pepper sauce', 87.20, 1, 2);

insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Traditional Indian bread with garlic topping', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Chicken cubes prepared with curry sauce and spices', 43, 1, 3);

insert into product (name, description, price, active, restaurant_id) values ('Ancho steak', 'Smooth and juicy cut, with two fingers thick, removed from the front of the fillet', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Very tasty cut, with a T-shaped bone, with the tender loin on one side and the mignon fillet on the other', 89, 1, 4);

insert into product (name, description, price, active, restaurant_id) values ('X-Tudo sandwich', 'Sandwich with lots of cheese, beef hamburger, bacon, egg, salad and mayonnaise', 19, 1, 5);

insert into product (name, description, price, active, restaurant_id) values ('Termite Skewer', 'Accompanies farofa, cassava and vinaigrette', 8, 1, 6);

insert into groupinge (id, name) values (1, 'Manager'), (2, 'Salesman'), (3, 'Secretary'), (4, 'Register');

insert into user (id, name, email, password, date_register) values (1, 'João da Silva', 'joao.ger@gmail.com', '123', utc_timestamp);
insert into user (id, name, email, password, date_register) values (2, 'Maria Joaquina', 'maria.vnd@gmail.com', '123', utc_timestamp);
insert into user (id, name, email, password, date_register) values (3, 'José Souza', 'jose.aux@gmail.com', '123', utc_timestamp);
insert into user (id, name, email, password, date_register) values (4, 'Sebastião Martins', 'sebastiao.cad@gmail.com', '123', utc_timestamp);
insert into user (id, name, email, password, date_register) values (5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);

insert into groupinge_permission (groupinge_id , permission_id ) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into user_groupinge (user_id, groupinge_id) values (1, 1), (1, 2), (2, 2);

insert into restaurant_responsible_user (restaurant_id, user_id) values (1, 5), (3, 5);

insert into purchase_order (id, uuid, restaurant_id, user_client_id , payment_method_id, address_city_id , address_zip_code, address_street, address_number, address_complement, address_district, status, date_register, subtotal, shipping_fee, total_value) values (1, 'cd056061-46dc-4bbb-8a7a-85e9e8e8196c', 1, 1, 1, 1, '14098-583', 'Rua Walter Leite de Souza', '680', 'Ap 201', 'Centro', 'CREATED', utc_timestamp, 298.90, 10, 308.90);

insert into item_purchase_order (id, purchase_order_id , product_id , quantity, unit_price, total_price, observation) values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_purchase_order (id, purchase_order_id , product_id , quantity, unit_price, total_price, observation) values (2, 1, 2, 2, 110, 220, 'Less spicy, please');

insert into purchase_order (id, uuid, restaurant_id, user_client_id , payment_method_id, address_city_id , address_zip_code, address_street, address_number, address_complement, address_district, status, date_register, subtotal, shipping_fee, total_value) values (2, 'd4ecb71b-09a4-4f8b-bafd-4c3855d39673', 4, 1, 2, 1, '14027-125', 'Rua Giselda de Mello Silva', '300', 'Casa 2', 'Jardim Nova Aliança Sul', 'CREATED', utc_timestamp, 79, 0, 79);

insert into item_purchase_order (id, purchase_order_id , product_id , quantity, unit_price, total_price, observation) values (3, 2, 6, 1, 79, 79, 'Medium rare');