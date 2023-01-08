package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoIdInput {
    @Schema(description = "ID do estado", example = "1")
    @NotNull
    private Long id;
}
