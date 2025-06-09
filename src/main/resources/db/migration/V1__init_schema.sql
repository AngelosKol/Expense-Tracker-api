DROP TABLE IF EXISTS TRANSACTION_DETAILS;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS SHOP;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS _user;

CREATE TABLE IF NOT EXISTS shop (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS  _user (
    id BIGINT PRIMARY KEY,
    firstname VARCHAR(255)  NOT NULL,
    lastname VARCHAR(255)  NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS token (
	id BIGINT ,
	expired bool ,
	revoked bool ,
	"token" varchar(255) UNIQUE,
	token_type varchar(255) ,
	user_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT PRIMARY KEY,
    date DATE,
    shop_id INT,
    user_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(id),
    FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS transaction_details (
    transaction_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price NUMERIC(38, 2),
    PRIMARY KEY (transaction_id, product_id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);


