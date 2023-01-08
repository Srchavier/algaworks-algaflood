package com.algaworks.algafood.api.model.output;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {

    @Schema(description = "ID da cozinha", example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @Schema(description = "Nome da cozinha", example = "cozinha 1")
    @JsonView(RestauranteView.Resumo.class) 
    private String nome;

}
