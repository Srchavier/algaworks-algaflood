create table permissao (
    id bigint not null auto_increment,
    nome varchar(80) not null,
    descricao varchar(200) not null,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;