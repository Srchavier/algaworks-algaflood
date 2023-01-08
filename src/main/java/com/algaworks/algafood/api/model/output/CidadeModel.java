package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CidadeModel {

    @Schema(description = "ID da cidade", example = "1")
    private Long id;
    @Schema(description = "Nome da cidade", example = "brasilía")
    private String nome;
    @Schema(description = "Nome  do estado", example = "planaltina")
    private String estado;
    
}
