create table restaurante (
    id bigint not null auto_increment,
    nome varchar(60) not null,
    taxa_frete DECIMAL(7,2) not null,
    cozinha_id bigint not null,
    data_cadastro datetime not null,
    data_atualizacao datetime not null,

    endereco_cep varchar(60),
    endereco_logradouro varchar(60),
    endereco_numero varchar(60),
    endereco_complemeento varchar(60),
    endereco_bairro varchar(60),
    endereco_cidade_id bigint,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE restaurante ADD CONSTRAINT endereco_cidade_id FOREIGN KEY ( endereco_cidade_id ) REFERENCES cidade ( id );

ALTER TABLE restaurante ADD CONSTRAINT fk_cozinha FOREIGN KEY ( cozinha_id ) REFERENCES cozinha ( id );
