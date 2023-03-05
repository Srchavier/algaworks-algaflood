package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @Schema(description = "ID da cidade", example = "1")
    private Long id;
    @Schema(description = "Nome da cidade", example = "brasilía")
    private String nome;
    @Schema(description = "Nome  do estado")
    private EstadoModel estado;
    
}
