create table restaurante_forma_pagamento (
    restaurante_id bigint not null,
    forma_pagamento_id  bigint not null,
    primary key (restaurante_id, forma_pagamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT restaurante_forma_pagamento_restaurante_id FOREIGN KEY ( restaurante_id ) REFERENCES restaurante ( id );
ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT restaurante_forma_pagamento_forma_pagamento_id FOREIGN KEY ( forma_pagamento_id ) REFERENCES forma_pagamento ( id );

