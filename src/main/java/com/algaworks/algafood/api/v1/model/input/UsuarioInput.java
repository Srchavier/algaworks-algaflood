package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioInput {
    @Schema(example = "João da Silva", required = true)
    @NotBlank
    private String nome;
    @Schema(example = "joao.ger@algafood.com.br", required = true)
    @Email
    @NotBlank
    private String email;
}
