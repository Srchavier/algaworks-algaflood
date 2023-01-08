package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SenhaInput {
    @Schema(example = "123", required = true)
    @NotBlank
    private String senhaAtual;
    @Schema(example = "123", required = true)
    @NotBlank
    private String novaSenha;
}
