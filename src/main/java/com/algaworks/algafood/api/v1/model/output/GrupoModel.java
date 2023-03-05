package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Setter
@Getter
public class GrupoModel extends RepresentationModel<GrupoModel>{
    @Schema(description = "ID do grupo", example = "1")
    private Long id;

    @Schema(description = "Nome do grupo", example = "grupo 1")
    private String nome;

}
