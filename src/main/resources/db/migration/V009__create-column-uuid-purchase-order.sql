alter table purchase_order add uuid varchar(36) not null after id;
update purchase_order set uuid = uuid();
alter table purchase_order add constraint uk_purchase_order_uuid unique (uuid);