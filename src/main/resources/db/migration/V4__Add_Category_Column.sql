ALTER TABLE product ADD COLUMN category_id BIGINT;
ALTER TABLE product ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id);
