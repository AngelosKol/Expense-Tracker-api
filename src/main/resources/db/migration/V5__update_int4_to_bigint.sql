BEGIN;

--------------------------------------------------------------------------------
ALTER TABLE public.token
  ALTER COLUMN id TYPE BIGINT USING id::BIGINT;

--------------------------------------------------------------------------------
ALTER TABLE TRANSACTION
  DROP CONSTRAINT IF EXISTS transaction_user_id_fkey;
ALTER TABLE public.transaction
  ALTER COLUMN user_id TYPE BIGINT USING user_id::BIGINT;
ALTER TABLE public.transaction
  ADD CONSTRAINT transaction_user_id_fkey
    FOREIGN KEY (user_id) REFERENCES public._user(id);
COMMIT;
