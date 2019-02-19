CREATE SEQUENCE seq_user START WITH 1;

CREATE TABLE "user" (
    id                      	bigint not null,
    login                   	varchar(200) not null,
    password                	varchar(100) not null,
	email                   	varchar(200) not null,
    	nome		          		varchar(50),
    peso               		real,
    idade               		int,
    altura               	int,
    
    data_inicio            	timestamp,
    
    horario_oracao_1			time,
    horario_oracao_2			time,
    horario_oracao_3			time,
    pessoa_oracao_1       	varchar(100),
    pessoa_oracao_2			varchar(100),
    pessoa_oracao_3			varchar(100),
    alimento_1				varchar(100),
    alimento_2				varchar(100),
    alimento_3				varchar(100),
    
    avatar_binary          	bytea,
    avatar_file_name       	varchar(100),
    avatar_content_type    	varchar(100),
    avatar_size            	numeric(11),

    creation_date            timestamp,
    creation_author          varchar(200),
    last_modification_date   timestamp,
    last_modification_author varchar(200),
    is_enabled              	bool not null default true,
    version                  int default 0,

    constraint user_unique_1 unique (login),
    primary key (id)
);

CREATE SEQUENCE seq_habito START WITH 1;

CREATE TABLE habito (
    id						bigint not null,
    nome						varchar(100) not null,
    descricao				varchar(1000) not null,
    ordem					int not null default 0,
    is_enabled				bool not null default true,
    ocorrencia				int not null, -- 1: diária, 2: semanal
    dia_1					int not null default 0,
    dia_2					int not null default 0,
    dia_3					int not null default 0,
    dia_4					int not null default 0,
    dia_5					int not null default 0,
    dia_6					int not null default 0,
    dia_7					int not null default 0,
    
    link_video				varchar(1000) null,
    
    capa_binary          	bytea,
    capa_file_name       	varchar(100),
    capa_content_type    	varchar(100),
    capa_size            	numeric(11),

    creation_date            timestamp,
    creation_author          varchar(200),
    last_modification_date   timestamp,
    last_modification_author varchar(200),

    version                  int default 0,
    
    constraint habito_unique_1 unique (nome),
    primary key (id)
);

CREATE SEQUENCE seq_desafio START WITH 1;

CREATE TABLE desafio (
    id						bigint not null,
    nome						varchar(100) not null,
    descricao				varchar(1000) not null,
    ordem					int not null default 0,
    is_enabled				bool not null default true,
    ocorrencia				int not null, -- 1: diária, 2: semanal
    dia_1					int not null default 0,
    dia_2					int not null default 0,
    dia_3					int not null default 0,
    dia_4					int not null default 0,
    dia_5					int not null default 0,
    dia_6					int not null default 0,
    dia_7					int not null default 0,
    
    link_video				varchar(1000) null,
    
    capa_binary          	bytea,
    capa_file_name       	varchar(100),
    capa_content_type    	varchar(100),
    capa_size            	numeric(11),

    creation_date            timestamp,
    creation_author          varchar(200),
    last_modification_date   timestamp,
    last_modification_author varchar(200),

    version                  int default 0,
    
    constraint desafio_unique_1 unique (nome),
    primary key (id)
);

CREATE SEQUENCE seq_user_dia_habito START WITH 1;

CREATE TABLE user_dia_habito (
	id                      	bigint not null,
    user_id     				int not null,
    habito_id     			int not null,
    dia     					int not null,
    creation_date            timestamp,
    last_modification_date   timestamp,

    constraint user_dia_habito_fk_1 foreign key (user_id) references "user",
    constraint user_dia_habito_fk_2 foreign key (habito_id) references habito,
    constraint user_dia_habito_uk_1 unique (user_id, habito_id, dia),
    primary key (id)
);


CREATE SEQUENCE seq_user_dia_desafio START WITH 1;

CREATE TABLE user_dia_desafio (
	id                      	bigint not null,
    user_id     				int not null,
    desafio_id     			int not null,
    dia     					int not null,
    creation_date            timestamp,
    last_modification_date   timestamp,

    constraint user_dia_desafio_fk_1 foreign key (user_id) references "user",
    constraint user_dia_desafio_fk_2 foreign key (desafio_id) references desafio,
    constraint user_dia_desafio_uk_1 unique (user_id, desafio_id, dia),
    primary key (id)
);

CREATE SEQUENCE seq_user_dia_testemunho START WITH 1;

CREATE TABLE user_dia_testemunho (
	id                      	bigint not null,
    user_id     				int not null,
    dia     					int not null,
    testemunho				varchar(4000),
    creation_date            timestamp,
    last_modification_date   timestamp,

    constraint user_dia_testemunho_fk_1 foreign key (user_id) references "user",
    constraint user_dia_testemunho_uk_1 unique (user_id, dia),
    primary key (id)
);

CREATE TABLE "role" (
    id              bigint not null,
    role_name       varchar(100) not null,
    constraint role_unique_1 unique (role_name),
    primary key (id)
);

CREATE SEQUENCE seq_role START WITH 1;

CREATE TABLE user_role (
    user_id     int not null,
    role_id     int not null,

    constraint user_role_fk_1 foreign key (user_id) references "user",
    constraint user_role_fk_2 foreign key (role_id) references "role",
    primary key (user_id, role_id)
);

INSERT INTO "user" (id, nome, login, password, email, is_enabled, version) VALUES (nextval('seq_user'), 'Admin', 'admin', 'admin', 'admin@example.com', true, 1);

INSERT INTO "role" (id, role_name) VALUES (nextval('seq_role'), 'ROLE_ADMIN');
INSERT INTO "role" (id, role_name) VALUES (nextval('seq_role'), 'ROLE_USER');
INSERT INTO "role" (id, role_name) VALUES (nextval('seq_role'), 'ROLE_MONITORING');

INSERT INTO user_role (user_id, role_id) VALUES ((select id from "user" where login = 'admin'), (select id from "role" where role_name = 'ROLE_ADMIN'));
INSERT INTO user_role (user_id, role_id) VALUES ((select id from "user" where login = 'admin'), (select id from "role" where role_name = 'ROLE_USER'));
INSERT INTO user_role (user_id, role_id) VALUES ((select id from "user" where login = 'admin'), (select id from "role" where role_name = 'ROLE_MONITORING'));