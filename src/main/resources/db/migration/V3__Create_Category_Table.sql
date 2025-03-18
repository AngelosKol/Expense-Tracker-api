
create table public.category (
id BIGINT NOT NULL,
name varchar(255) NOT NULL,
CONSTRAINT "category_name_key" UNIQUE(name),
CONSTRAINT "category_pkey" PRIMARY KEY (id)
)
