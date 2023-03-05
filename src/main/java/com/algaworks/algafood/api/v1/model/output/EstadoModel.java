package com.algaworks.algafood.api.v1.model.output;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Setter
@Getter
public class EstadoModel extends RepresentationModel<EstadoModel> {
    @Schema(description = "ID  do estado", example = "1")
    private Long id;
    @Schema(description = "Nome  do estado", example = "planaltina")
    private String nome;
}
