
CREATE TABLE IF NOT EXISTS shop (
    id INT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS  _user (
    id INT PRIMARY KEY,
    firstname VARCHAR(255)  NOT NULL,
    lastname VARCHAR(255)  NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT PRIMARY KEY,
    date DATE,
    shop_id INT,
    user_id INT,
    FOREIGN KEY (shop_id) REFERENCES shop(id),
    FOREIGN KEY (user_id) REFERENCES _user(id)
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


