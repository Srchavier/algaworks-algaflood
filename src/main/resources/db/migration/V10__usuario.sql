create table usuario (
    id bigint not null auto_increment,
    nome varchar(80) not null,
    email varchar(80) not null,
    senha varchar(250) not null,
    data_cadastro datetime not null,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table usuario_grupo (
    usuario_id bigint not null,
    grupo_id  bigint not null,
    primary key (usuario_id, grupo_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE usuario_grupo ADD CONSTRAINT usuario_grupo_usuario_id FOREIGN KEY ( grupo_id ) REFERENCES grupo ( id );

ALTER TABLE usuario_grupo ADD CONSTRAINT usuario_grupo_grupo_id FOREIGN KEY ( usuario_id ) REFERENCES usuario ( id );