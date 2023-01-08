package com.algaworks.algafood.api.model.output;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {
    @Schema(example = "1")
    private Long produtoId;
    @Schema(example = "Porco com molho agridoce")
    private String produtoNome;
    @Schema(example = "2")
    private Integer quantidade;
    @Schema(example = "78.90")
    private BigDecimal precoUnitario;
    @Schema(example = "157.80")
    private BigDecimal precoTotal;
    @Schema(example = "Menos picante, por favor")
    private String observacao;            
}    
