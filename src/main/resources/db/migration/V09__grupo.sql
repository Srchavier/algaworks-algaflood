create table grupo (
    id bigint not null auto_increment,
    nome varchar(60) not null,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table grupo_permissao (
    grupo_id  bigint not null,
    permissao_id bigint not null,
    primary key (permissao_id, grupo_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE grupo_permissao ADD CONSTRAINT grupo_permissao_grupo_id FOREIGN KEY ( grupo_id ) REFERENCES grupo ( id );

ALTER TABLE grupo_permissao ADD CONSTRAINT grupo_permissao_permissao_id FOREIGN KEY ( permissao_id ) REFERENCES permissao ( id );