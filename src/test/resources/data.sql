INSERT INTO _user (id, firstname, lastname, email, password)
VALUES
  (1, 'Alice', 'Smith', 'alice@example.com', 'pw'),
  (2, 'Bob',   'Jones', 'bob@example.com',   'pw');


-- Insert multiple rows into the Shop table
INSERT INTO shop (id, name)
VALUES 
(1,'Masoutis'),
(2,'Vasilopoulos'),
(3,'Sklavenitis'),
(4,'Lidl'),
(5,'MyMarket');

-- Insert multiple rows into the Products table
INSERT INTO product (id,name)
VALUES
(0,'Cheese'),
(1,'Milk'),
(2,'Bread'),
(3,'Coffee'),
(4,'Chips'),
(5,'Fish'),
(6,'Meat'),
(7,'Mustard'),
(8,'Vegetables'),
(9,'Fruits');

insert into TRANSACTION (id,date,shop_id,user_id) values (102,'2024-07-02',1,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (103,'2024-06-02',1,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (104,'2024-03-02',1,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (105,'2024-04-20',1,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (106,'2024-05-17',1,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (107,'2024-04-19',5,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (108,'2024-05-01',4,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (109,'2024-05-02',5,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (110,'2024-05-03',5,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (111,'2023-12-17',3,2);
insert into TRANSACTION (id,date,shop_id,user_id) values (112,'2024-06-02',1,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (113,'2024-03-02',1,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (114,'2024-04-20',1,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (115,'2024-05-3',1,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (116,'2024-05-5',5,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (117,'2024-05-1',3,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (118,'2024-06-01',4,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (119,'2024-06-02',5,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (120,'2024-06-03',5,1);
insert into TRANSACTION (id,date,shop_id,user_id) values (121,'2024-07-02',1,1);

insert into transaction_details (transaction_id,product_id,quantity,price) values (102,6,2,8);
insert into transaction_details (transaction_id,product_id,quantity,price) values (103,6,2,8);
insert into transaction_details (transaction_id,product_id,quantity,price) values (104,6,2,8);
insert into transaction_details (transaction_id,product_id,quantity,price) values (105,6,2,8);
insert into transaction_details (transaction_id,product_id,quantity,price) values (108,1,2,3);
insert into transaction_details (transaction_id,product_id,quantity,price) values (109,2,2,2);
insert into transaction_details (transaction_id,product_id,quantity,price) values (110,6,2,8);
insert into transaction_details (transaction_id,product_id,quantity,price) values (111,6,5,13);
insert into transaction_details (transaction_id,product_id,quantity,price) values (112,6,2,2);
insert into transaction_details (transaction_id,product_id,quantity,price) values (113,6,2,4);
insert into transaction_details (transaction_id,product_id,quantity,price) values (114,6,2,5);
insert into transaction_details (transaction_id,product_id,quantity,price) values (115,6,2,2);
insert into transaction_details (transaction_id,product_id,quantity,price) values (116,1,2,32);
insert into transaction_details (transaction_id,product_id,quantity,price) values (117,2,2,22);
insert into transaction_details (transaction_id,product_id,quantity,price) values (118,6,2,15);
insert into transaction_details (transaction_id,product_id,quantity,price) values (119,6,2,6);
insert into transaction_details (transaction_id,product_id,quantity,price) values (120,6,2,5);
