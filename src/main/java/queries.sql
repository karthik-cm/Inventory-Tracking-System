#MySQL Database queries:

CREATE DATABASE its;

USE its;

CREATE TABLE INVENTORY_WAREHOUSE_DETAILS(
	warehouse_id int NOT NULL AUTO_INCREMENT,
	warehouse_name varchar(255) NOT NULL,
	location varchar(100) NOT NULL,
	zipcode int NOT NULL,
	is_active varchar(10),
	record_created_by varchar(50),
	record_created_dt TIMESTAMP,
	record_updated_by varchar(50),
	record_updated_dt TIMESTAMP,
	PRIMARY KEY (warehouse_id),
	UNIQUE (warehouse_name, location, zipcode)
);

CREATE TABLE INVENTORY_ITEMS_DETAILS(
	item_id int NOT NULL AUTO_INCREMENT,
	warehouse_id int NOT NULL,
	item_name varchar(255) NOT NULL,
	quantity int NOT NULL,
	price decimal(10,2) NOT NULL,
	lot_no int NOT NULL,
	received_date DATE,
	is_active varchar(10),
	record_created_by varchar(50),
	record_created_dt TIMESTAMP,
	record_updated_by varchar(50),
	record_updated_dt TIMESTAMP,
	PRIMARY KEY (item_id),
	FOREIGN KEY (warehouse_id) REFERENCES INVENTORY_WAREHOUSE_DETAILS (warehouse_id)
);
