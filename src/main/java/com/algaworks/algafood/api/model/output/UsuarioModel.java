package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioModel {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "João da Silva")
    private String nome;
    @Schema(example = "joao.ger@algafood.com.br")
    private String email;

}
