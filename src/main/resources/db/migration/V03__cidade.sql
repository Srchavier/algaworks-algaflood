create table cidade (
    id bigint not null auto_increment,
    nome varchar(60) not null,
    estado_id bigint not null,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE cidade ADD CONSTRAINT fk_cidade FOREIGN KEY ( estado_id ) REFERENCES estado ( id ) ;
