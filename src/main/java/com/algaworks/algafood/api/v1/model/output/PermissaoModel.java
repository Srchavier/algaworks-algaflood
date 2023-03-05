package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;

@Relation(collectionRelation = "premissoes")
@Data
public class PermissaoModel extends RepresentationModel<PermissaoModel>{
    private Long id;
    private String nome;
    private String descricao;
}
