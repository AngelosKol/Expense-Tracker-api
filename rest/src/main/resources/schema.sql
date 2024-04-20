CREATE TABLE IF NOT EXISTS shop (
    shop_id INT PRIMARY KEY,
    shop_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction (
    transaction_id INT PRIMARY KEY,
    transaction_date DATE,
    shop_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(shop_id)
);

CREATE TABLE IF NOT EXISTS product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction_details (
    transaction_details_id INT PRIMARY KEY,
    transaction_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
