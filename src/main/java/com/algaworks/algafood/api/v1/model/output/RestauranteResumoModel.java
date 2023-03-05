package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantesResumos")
@Setter
@Getter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Thai Gourmet")
    private String nome;   
}  