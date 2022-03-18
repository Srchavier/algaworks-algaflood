create table produto (
    id bigint not null auto_increment,
    nome varchar(80) not null,
    descricao varchar(200) not null,
    preco DECIMAL(7,2) not null,
    ativo boolean DEFAULT false,
    restaurante_id bigint,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE produto ADD CONSTRAINT produto_restaurante_id FOREIGN KEY ( restaurante_id ) REFERENCES restaurante ( id );
