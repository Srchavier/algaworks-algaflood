package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

    @Schema(example = "Cartão de crédito", required = true)
    @NotBlank
    private String descricao;
    
}
