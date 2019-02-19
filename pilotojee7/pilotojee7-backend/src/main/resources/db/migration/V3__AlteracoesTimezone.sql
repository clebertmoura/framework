ALTER TABLE desafio ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE desafio ALTER COLUMN last_modification_date TYPE timestamp with time zone;

ALTER TABLE habito ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE habito ALTER COLUMN last_modification_date TYPE timestamp with time zone;

ALTER TABLE "user" ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE "user" ALTER COLUMN last_modification_date TYPE timestamp with time zone;
ALTER TABLE "user" ALTER COLUMN data_inicio TYPE timestamp with time zone;
ALTER TABLE "user" ALTER COLUMN horario_oracao_1 TYPE time with time zone;
ALTER TABLE "user" ALTER COLUMN horario_oracao_2 TYPE time with time zone;
ALTER TABLE "user" ALTER COLUMN horario_oracao_3 TYPE time with time zone;

ALTER TABLE user_dia_desafio ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE user_dia_desafio ALTER COLUMN last_modification_date TYPE timestamp with time zone;

ALTER TABLE user_dia_habito ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE user_dia_habito ALTER COLUMN last_modification_date TYPE timestamp with time zone;

ALTER TABLE user_dia_testemunho ALTER COLUMN creation_date TYPE timestamp with time zone;
ALTER TABLE user_dia_testemunho ALTER COLUMN last_modification_date TYPE timestamp with time zone;