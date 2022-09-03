package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioInput {
    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
}
