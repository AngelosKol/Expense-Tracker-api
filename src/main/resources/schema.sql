DROP TABLE IF EXISTS TRANSACTION_DETAILS;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS SHOP;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE IF NOT EXISTS shop (
    id INT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT PRIMARY KEY,
    date DATE,
    shop_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(id)
);

CREATE TABLE IF NOT EXISTS product (
    id INT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS transaction_details (
    transaction_id INT,
    product_id INT,
    quantity INT,
    price NUMERIC(38, 2),
    PRIMARY KEY (transaction_id, product_id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

