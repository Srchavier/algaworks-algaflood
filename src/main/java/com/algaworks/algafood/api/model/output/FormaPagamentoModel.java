package com.algaworks.algafood.api.model.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {
    
    @Schema(example = "1", description = "ID forma pagamento")
    private Long id;
    @Schema(example = "Cartão de crédito", description = "Descrição de uma forma pagamento")
    private String descricao;
}
