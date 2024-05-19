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
(1,'Milk'),
(2,'Bread'),
(3,'Coffee'),
(4,'Chips'),
(5,'Fish'),
(6,'Meat'),
(7,'Mustard'),
(8,'Vegetables'),
(9,'Fruits'),
(0,'Cheese');


insert into "transaction" (id,date,shop_id) values (106,'2024-05-17',1);
insert into "transaction" (id,date,shop_id) values (107,'2024-04-19',5);
insert into "transaction" (id,date,shop_id) values (111,'2023-12-17',3);
insert into "transaction" (id,date,shop_id) values (108,'2024-05-01',4);
insert into "transaction" (id,date,shop_id) values (109,'2024-05-02',5);
insert into "transaction" (id,date,shop_id) values (110,'2024-05-03',5);


insert into transaction_details (transaction_id,product_id,quantity,price) values (108,1,2,3);
insert into transaction_details (transaction_id,product_id,quantity,price) values (109,2,2,2);
insert into transaction_details (transaction_id,product_id,quantity,price) values (110,6,2,8);
