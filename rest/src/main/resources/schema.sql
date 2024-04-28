CREATE TABLE IF NOT EXISTS shop (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT PRIMARY KEY,
    date DATE,
    shop_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(id)
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
    FOREIGN KEY (transaction_id) REFERENCES transaction(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
