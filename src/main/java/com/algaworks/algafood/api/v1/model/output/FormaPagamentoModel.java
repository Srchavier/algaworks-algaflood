package com.algaworks.algafood.api.v1.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamentos")
@Getter
@Setter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel>{
    
    @Schema(example = "1", description = "ID forma pagamento")
    private Long id;
    @Schema(example = "Cartão de crédito", description = "Descrição de uma forma pagamento")
    private String descricao;
}
