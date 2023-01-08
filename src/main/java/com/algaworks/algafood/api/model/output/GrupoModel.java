package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GrupoModel {
    @Schema(description = "ID do grupo", example = "1")
    private Long id;

    @Schema(description = "Nome do grupo", example = "grupo 1")
    private String nome;

}
