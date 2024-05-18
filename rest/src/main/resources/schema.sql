DROP TABLE IF EXISTS TRANSACTION_DETAILS;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS SHOP;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE IF NOT EXISTS shop (
    id SERIAL  PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS transaction (
    id SERIAL  PRIMARY KEY,
    date DATE,
    shop_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(id)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL  PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction_details (
    id SERIAL  PRIMARY KEY,
    transaction_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
