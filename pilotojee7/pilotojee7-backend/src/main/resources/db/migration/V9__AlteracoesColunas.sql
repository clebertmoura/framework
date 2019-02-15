ALTER TABLE user_dia_testemunho ADD COLUMN arquivo_binary bytea;
ALTER TABLE user_dia_testemunho ADD COLUMN arquivo_file_name varchar(100);
ALTER TABLE user_dia_testemunho ADD COLUMN arquivo_content_type varchar(100);
ALTER TABLE user_dia_testemunho ADD COLUMN arquivo_size numeric(11);