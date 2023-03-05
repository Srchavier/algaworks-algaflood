package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.api.v1.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @Schema(description = "ID da cozinha", example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @Schema(description = "Nome da cozinha", example = "cozinha 1")
    @JsonView(RestauranteView.Resumo.class) 
    private String nome;

}
