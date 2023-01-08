package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioComSenhaInput extends UsuarioInput {
    @Schema(description = "Senha do usuário", required = true, example = "*****")
    @NotBlank
    private String senha;
    @Schema(description = "Confirmação senha do usuário", required = true, example = "*****")
    @NotBlank
    private String senhaConfirmacao; 
}
