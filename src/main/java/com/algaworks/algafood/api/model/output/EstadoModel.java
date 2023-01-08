package com.algaworks.algafood.api.model.output;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EstadoModel {
    @Schema(description = "ID  do estado", example = "1")
    private Long id;
    @Schema(description = "Nome  do estado", example = "planaltina")
    private String nome;
}
