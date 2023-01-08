package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CidadeIdInput {
    @Schema(example = "1", required = true)
    @NotNull
    private Long id;
}
