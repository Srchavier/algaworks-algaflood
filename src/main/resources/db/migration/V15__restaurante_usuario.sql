create table restaurante_usuario_responsavel (
    restaurante_id bigint not null,
    usuario_id  bigint not null,
    primary key (restaurante_id, usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_restaurante_id FOREIGN KEY ( restaurante_id ) REFERENCES restaurante ( id );
ALTER TABLE restaurante_usuario_responsavel ADD CONSTRAINT fk_restaurante_usuario_usuario_id FOREIGN KEY ( usuario_id ) REFERENCES usuario ( id );