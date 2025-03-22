CREATE TABLE public.family (
id BIGINT NOT NULL,
name varchar(255) NOT NULL,
CONSTRAINT "family_pkey" PRIMARY KEY (id),
CONSTRAINT "family_name_key" UNIQUE(name)
)
