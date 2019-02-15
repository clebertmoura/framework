ALTER TABLE "user" ADD COLUMN is_email_confirmado bool not null default false;
ALTER TABLE "user" ADD COLUMN codigo_confirmacao varchar(255);