
create table public.category (
id BIGINT NOT NULL,
name varchar(40) NOT NULL,
family varchar(40) NOT NULL,
CONSTRAINT "category_name_key" UNIQUE(name),
CONSTRAINT "category_pkey" PRIMARY KEY (id)
);

ALTER TABLE product ADD COLUMN category_id BIGINT;
ALTER TABLE product ADD CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id);
