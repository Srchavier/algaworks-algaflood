package com.algaworks.algafood.api.model.output;

import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteApenasNomeModel {
    @Schema(example = "1")
	private Long id;

	@Schema(example = "Thai Gourmet")
	private String nome;
}
