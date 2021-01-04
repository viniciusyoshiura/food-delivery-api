alter table payment_method add date_update datetime null;
update payment_method set date_update = utc_timestamp;
alter table payment_method modify date_update datetime not null;