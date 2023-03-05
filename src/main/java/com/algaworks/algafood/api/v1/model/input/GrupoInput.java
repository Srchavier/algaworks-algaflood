package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GrupoInput {
    
    @Schema(description = "Nome  do grupo", example = "grupo 1")
    @NotBlank
    private String nome;
}
